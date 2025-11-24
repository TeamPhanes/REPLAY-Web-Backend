package phanes.replay.gathering.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.SortField;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import phanes.replay.gathering.dto.GatheringCommentDto;
import phanes.replay.gathering.dto.MyCommentDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static phanes.replay.tables.GatheringComment.GATHERING_COMMENT;
import static phanes.replay.tables.Users.USERS;

@Repository
@RequiredArgsConstructor
public class GatheringCommentJooqRepository {

    private final DSLContext dsl;

    public Page<GatheringCommentDto> findParentCommentByGatheringId(Pageable pageable, Long gatheringId) {
        Sort.Order order = pageable.getSort().isEmpty() ? Sort.Order.asc("createdAt") : pageable.getSort().getOrderFor("createdAt");
        SortField<LocalDateTime> sortField = order.isAscending() ? GATHERING_COMMENT.CREATED_AT.asc() : GATHERING_COMMENT.CREATED_AT.desc();
        List<GatheringCommentDto> contents = dsl.select(GATHERING_COMMENT.ID, GATHERING_COMMENT.USER_ID, GATHERING_COMMENT.CONTENT, GATHERING_COMMENT.PARENT_ID, GATHERING_COMMENT.CREATED_AT, GATHERING_COMMENT.UPDATED_AT)
                .select(USERS.NICKNAME, USERS.PROFILE_IMAGE, USERS.EMAIL)
                .from(GATHERING_COMMENT)
                .join(USERS).on(GATHERING_COMMENT.USER_ID.eq(USERS.ID))
                .where(GATHERING_COMMENT.GATHERING_ID.eq(gatheringId).and(GATHERING_COMMENT.PARENT_ID.isNull()))
                .orderBy(sortField)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchInto(GatheringCommentDto.class);
        Long total = dsl.selectCount()
                .from(GATHERING_COMMENT)
                .where(GATHERING_COMMENT.GATHERING_ID.eq(gatheringId).and(GATHERING_COMMENT.PARENT_ID.isNull()))
                .fetchOneInto(Long.class);
        return new PageImpl<>(contents, pageable, total == null ? 0 : total);
    }

    public Map<Long, List<GatheringCommentDto>> findChileCommentById(List<Long> commentIdList) {
        return dsl.select(GATHERING_COMMENT.ID, GATHERING_COMMENT.USER_ID, GATHERING_COMMENT.CONTENT, GATHERING_COMMENT.PARENT_ID, GATHERING_COMMENT.CREATED_AT, GATHERING_COMMENT.UPDATED_AT)
                .select(USERS.NICKNAME, USERS.PROFILE_IMAGE, USERS.EMAIL)
                .from(GATHERING_COMMENT)
                .join(USERS).on(GATHERING_COMMENT.USER_ID.eq(USERS.ID))
                .where(GATHERING_COMMENT.PARENT_ID.in(commentIdList))
                .orderBy(GATHERING_COMMENT.CREATED_AT.asc())
                .fetchInto(GatheringCommentDto.class)
                .stream()
                .collect(Collectors.groupingBy(GatheringCommentDto::getParentId));
    }

    public List<MyCommentDto> findAllByUserId(Long userId, Pageable pageable) {
        return dsl.select(GATHERING_COMMENT.CONTENT, GATHERING_COMMENT.CREATED_AT, GATHERING_COMMENT.GATHERING_ID)
                .from(GATHERING_COMMENT)
                .where(GATHERING_COMMENT.USER_ID.eq(userId))
                .orderBy(GATHERING_COMMENT.CREATED_AT.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchInto(MyCommentDto.class);
    }
}