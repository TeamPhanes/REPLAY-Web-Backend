package phanes.replay.gathering.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import phanes.replay.gathering.dto.GatheringCommentDto;

import java.util.List;

import static phanes.replay.tables.GatheringComment.GATHERING_COMMENT;
import static phanes.replay.tables.Users.USERS;

@Repository
@RequiredArgsConstructor
public class GatheringCommentJooqRepository {

    private final DSLContext dsl;

    public List<GatheringCommentDto> findAllByGatheringId(Long gatheringId) {
        return dsl.select(GATHERING_COMMENT.ID, GATHERING_COMMENT.USER_ID, GATHERING_COMMENT.CONTENT, GATHERING_COMMENT.PARENT_ID, GATHERING_COMMENT.CREATED_AT, GATHERING_COMMENT.UPDATED_AT)
                .select(USERS.NICKNAME, USERS.PROFILE_IMAGE, USERS.EMAIL)
                .from(GATHERING_COMMENT)
                .join(USERS).on(GATHERING_COMMENT.USER_ID.eq(USERS.ID))
                .where(GATHERING_COMMENT.GATHERING_ID.eq(gatheringId))
                .fetchInto(GatheringCommentDto.class);
    }
}