package phanes.replay.user.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import phanes.replay.user.dto.user.MyParticipantGatheringDto;
import phanes.replay.user.dto.user.MyVisitThemeDto;
import phanes.replay.utils.JooqRepositoryUtils;

import java.util.List;

import static phanes.replay.Tables.GATHERING;
import static phanes.replay.tables.Cafe.CAFE;
import static phanes.replay.tables.GatheringMember.GATHERING_MEMBER;
import static phanes.replay.tables.Review.REVIEW;
import static phanes.replay.tables.Spot.SPOT;
import static phanes.replay.tables.Theme.THEME;
import static phanes.replay.tables.ThemeVisit.THEME_VISIT;

@Repository
@RequiredArgsConstructor
public class UserJooqRepository {

    private final DSLContext dsl;
    private final JooqRepositoryUtils utils;

    public Page<MyVisitThemeDto> findVisitThemeById(Long userId, Pageable pageable) {
        List<MyVisitThemeDto> contents = dsl.select(CAFE.NAME.as("cafeName"), SPOT.NAME.as("spotName"))
                .select(THEME.ID, THEME.TITLE, THEME.IMAGE, THEME_VISIT.CREATED_AT.as("visitDate"))
                .select(REVIEW.SCORE, REVIEW.THEME_REVIEW, REVIEW.STORY_REVIEW, REVIEW.LEVEL_REVIEW, REVIEW.HINT, REVIEW.NUMBER_OF_PLAYER, REVIEW.IS_SUCCESS, REVIEW.CONTENT, REVIEW.IMAGE.as("reviewImage"))
                .from(THEME_VISIT)
                .join(REVIEW).on(THEME_VISIT.USER_ID.eq(REVIEW.USER_ID))
                .join(THEME).on(THEME_VISIT.THEME_ID.eq(THEME.ID))
                .join(SPOT).on(THEME.SPOT_ID.eq(SPOT.ID))
                .join(CAFE).on(SPOT.CAFE_ID.eq(CAFE.ID))
                .where(THEME_VISIT.USER_ID.eq(userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchInto(MyVisitThemeDto.class);
        Long totalCount = dsl.selectCount()
                .from(THEME_VISIT)
                .where(THEME_VISIT.USER_ID.eq(userId))
                .fetchOneInto(Long.class);
        return new PageImpl<>(contents, pageable, totalCount == null ? 0 : totalCount);
    }

    public Page<MyParticipantGatheringDto> findParticipantGatheringById(Long userId, Pageable pageable) {
        List<MyParticipantGatheringDto> contents = dsl.select(GATHERING.ID, GATHERING.NAME, GATHERING.DATE, GATHERING.CAPACITY, utils.isLikedGathering(userId))
                .select(THEME.ID.as("themeId"), THEME.TITLE, THEME.PLAYTIME, THEME.LEVEL, THEME.IMAGE)
                .select(SPOT.ADDRESS, SPOT.NAME.as("spotName"), CAFE.NAME.as("cafeName"))
                .from(GATHERING_MEMBER)
                .join(GATHERING).on(GATHERING_MEMBER.GATHERING_ID.eq(GATHERING.ID))
                .join(THEME).on(GATHERING.THEME_ID.eq(THEME.ID))
                .join(SPOT).on(THEME.SPOT_ID.eq(SPOT.ID))
                .join(CAFE).on(SPOT.CAFE_ID.eq(CAFE.ID))
                .where(GATHERING_MEMBER.USER_ID.eq(userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchInto(MyParticipantGatheringDto.class);
        Long totalCount = dsl.selectCount()
                .from(GATHERING_MEMBER)
                .where(GATHERING_MEMBER.USER_ID.eq(userId))
                .fetchOneInto(Long.class);
        return new PageImpl<>(contents, pageable, totalCount == null ? 0 : totalCount);
    }
}