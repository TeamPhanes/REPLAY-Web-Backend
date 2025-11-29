package phanes.replay.gathering.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Row2;
import org.jooq.impl.DSL;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import phanes.replay.gathering.dto.GatheringDetailDto;
import phanes.replay.gathering.dto.GatheringDto;
import phanes.replay.utils.JooqRepositoryUtils;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static phanes.replay.tables.Gathering.GATHERING;
import static phanes.replay.tables.GatheringContent.GATHERING_CONTENT;
import static phanes.replay.tables.GatheringMember.GATHERING_MEMBER;
import static phanes.replay.tables.Genre.GENRE;
import static phanes.replay.tables.Spot.SPOT;
import static phanes.replay.tables.Theme.THEME;
import static phanes.replay.tables.ThemeContent.THEME_CONTENT;

@Repository
@RequiredArgsConstructor
public class GatheringJooqRepository {

    private final DSLContext dsl;
    private final JooqRepositoryUtils utils;


    public Page<GatheringDto> findAll(Long userId, Long themeId, Pageable pageable, List<String> locations, List<String> genres) {
        Condition where = DSL.trueCondition();
        if (themeId != null) {
            where = where.and(GATHERING.THEME_ID.eq(themeId));
        } else {
            if (!CollectionUtils.isEmpty(locations)) {
                Set<Row2<String, String>> pairs = new LinkedHashSet<>();
                Set<String> stateOnly = new LinkedHashSet<>();
                for (String location : locations) {
                    String[] split = location.trim().split(" ");
                    if (split.length == 2) {
                        pairs.add(DSL.row(split[0].trim(), split[1].trim()));
                    } else if (split.length == 1) {
                        stateOnly.add(split[0].trim());
                    } else {
                        throw new RuntimeException();
                    }
                }
                if (!pairs.isEmpty()) {
                    where = where.and(DSL.row(SPOT.STATE, SPOT.CITY).in(pairs));
                }
                if (!stateOnly.isEmpty()) {
                    where = where.and(SPOT.STATE.in(stateOnly));
                }
            }
            if (!CollectionUtils.isEmpty(genres)) {
                where = where.andExists(
                        dsl.selectOne()
                                .from(GENRE)
                                .where(GENRE.THEME_ID.eq(THEME.ID)
                                        .and(GENRE.NAME.in(genres))));
            }
        }
        List<GatheringDto> contents = dsl.select(GATHERING.ID, GATHERING.THEME_ID, GATHERING.NAME, GATHERING.DATE, GATHERING.CAPACITY)
                .select(THEME.TITLE, THEME.PLAYTIME, THEME.LEVEL, THEME.IMAGE, SPOT.ADDRESS)
                .select(utils.isLikedGathering(userId), DSL.count(GATHERING_MEMBER.USER_ID).as("participantCount"))
                .from(GATHERING)
                .join(GATHERING_MEMBER).on(GATHERING.ID.eq(GATHERING_MEMBER.GATHERING_ID))
                .join(THEME).on(GATHERING.THEME_ID.eq(THEME.ID))
                .join(SPOT).on(THEME.SPOT_ID.eq(SPOT.ID))
                .where(where)
                .groupBy(GATHERING.ID, GATHERING.THEME_ID, GATHERING.NAME, GATHERING.DATE, GATHERING.CAPACITY, THEME.TITLE, THEME.PLAYTIME, THEME.LEVEL, THEME.IMAGE, SPOT.ADDRESS)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchInto(GatheringDto.class);
        Long total = dsl.selectCount()
                .from(GATHERING)
                .join(THEME).on(GATHERING.THEME_ID.eq(THEME.ID))
                .join(SPOT).on(THEME.SPOT_ID.eq(SPOT.ID))
                .where(where)
                .fetchOne(0, Long.class);
        return new PageImpl<>(contents, pageable, total == null ? 0L : total);
    }

    public GatheringDetailDto findById(Long userId, Long gatheringId) {
        return dsl.select(GATHERING.fields())
                .select(GATHERING_CONTENT.CONTENT, GATHERING_CONTENT.PRICE, GATHERING_CONTENT.IS_INDIVIDUAL)
                .select(THEME.TITLE, THEME_CONTENT.IMAGE, SPOT.ADDRESS, utils.isLikedGathering(userId))
                .from(GATHERING)
                .join(GATHERING_CONTENT).on(GATHERING_CONTENT.GATHERING_ID.eq(GATHERING.ID))
                .join(THEME).on(GATHERING.THEME_ID.eq(THEME.ID))
                .join(THEME_CONTENT).on(THEME_CONTENT.THEME_ID.eq(THEME.ID))
                .join(SPOT).on(THEME.SPOT_ID.eq(SPOT.ID))
                .where(GATHERING.ID.eq(gatheringId))
                .fetchOneInto(GatheringDetailDto.class);
    }

    public Page<GatheringDto> findAllByDate(Long userId, Pageable pageable, LocalDateTime date) {
        Condition where = GATHERING.DATE.between(date.toLocalDate().atStartOfDay(), date.toLocalDate().plusDays(1).atStartOfDay().minusNanos(1));
        List<GatheringDto> contents = dsl.select(GATHERING.ID, GATHERING.THEME_ID, GATHERING.NAME, GATHERING.DATE, GATHERING.CAPACITY)
                .select(THEME.TITLE, THEME.PLAYTIME, THEME.LEVEL, THEME.IMAGE, SPOT.ADDRESS)
                .select(utils.isLikedGathering(userId), DSL.count(GATHERING_MEMBER.USER_ID).as("participantCount"))
                .from(GATHERING)
                .join(GATHERING_MEMBER).on(GATHERING.ID.eq(GATHERING_MEMBER.GATHERING_ID))
                .join(THEME).on(GATHERING.THEME_ID.eq(THEME.ID))
                .join(SPOT).on(THEME.SPOT_ID.eq(SPOT.ID))
                .where(where)
                .groupBy(GATHERING.ID, GATHERING.THEME_ID, GATHERING.NAME, GATHERING.DATE, GATHERING.CAPACITY, THEME.TITLE, THEME.PLAYTIME, THEME.LEVEL, THEME.IMAGE, SPOT.ADDRESS)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchInto(GatheringDto.class);
        Long total = dsl.selectCount()
                .from(GATHERING)
                .join(THEME).on(GATHERING.THEME_ID.eq(THEME.ID))
                .join(SPOT).on(THEME.SPOT_ID.eq(SPOT.ID))
                .where(where)
                .fetchOne(0, Long.class);
        return new PageImpl<>(contents, pageable, total == null ? 0L : total);
    }
}