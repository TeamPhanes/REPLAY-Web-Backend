package phanes.replay.gathering.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.impl.DSL;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import phanes.replay.gathering.dto.GatheringDto;
import phanes.replay.theme.repository.JooqRepositoryUtils;

import java.util.List;

import static phanes.replay.tables.Cafe.CAFE;
import static phanes.replay.tables.Gathering.GATHERING;
import static phanes.replay.tables.GatheringMember.GATHERING_MEMBER;
import static phanes.replay.tables.Spot.SPOT;
import static phanes.replay.tables.Theme.THEME;

@Repository
@RequiredArgsConstructor
public class GatheringJooqRepository {

    private final DSLContext dsl;
    private final JooqRepositoryUtils utils;


    public PageImpl<GatheringDto> findByThemeId(Long userId, Pageable pageable, Long themeId) {
        Field<Integer> participantCount =
                DSL.selectCount()
                        .from(GATHERING_MEMBER)
                        .where(GATHERING_MEMBER.GATHERING_ID.eq(GATHERING.ID))
                        .asField("participantCount");
        List<GatheringDto> contents = dsl.select(GATHERING.ID, GATHERING.THEME_ID, GATHERING.NAME, GATHERING.IMAGE, GATHERING.DATE, GATHERING.CAPACITY)
                .select(THEME.PLAYTIME, THEME.LEVEL)
                .select(SPOT.NAME.as("spotName"), SPOT.ADDRESS, CAFE.NAME.as("cafeName"))
                .select(utils.isLiked(userId), participantCount)
                .from(GATHERING)
                .leftJoin(GATHERING_MEMBER).on(GATHERING_MEMBER.GATHERING_ID.eq(GATHERING.ID))
                .join(THEME).on(GATHERING.THEME_ID.eq(THEME.ID))
                .join(SPOT).on(THEME.SPOT_ID.eq(SPOT.ID))
                .join(CAFE).on(SPOT.CAFE_ID.eq(CAFE.ID))
                .where(GATHERING.THEME_ID.eq(themeId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchInto(GatheringDto.class);
        Long total = dsl.selectCount()
                .from(GATHERING)
                .where(GATHERING.THEME_ID.eq(themeId))
                .fetchOne(0, Long.class);
        return new PageImpl<>(contents, pageable, total == null ? 0L : total);
    }
}