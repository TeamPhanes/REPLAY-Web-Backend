package phanes.replay.gathering.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import phanes.replay.gathering.dto.GatheringDto;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static phanes.replay.tables.Gathering.GATHERING;
import static phanes.replay.tables.GatheringLike.GATHERING_LIKE;
import static phanes.replay.tables.GatheringMember.GATHERING_MEMBER;
import static phanes.replay.tables.Genre.GENRE;
import static phanes.replay.tables.Spot.SPOT;
import static phanes.replay.tables.Theme.THEME;

@Repository
@RequiredArgsConstructor
public class GatheringLikeJooqRepository {

    private final DSLContext dsl;

    public Page<GatheringDto> findAllByLike(Long userId, Pageable pageable, List<String> locations, List<String> genres) {
        Condition where = DSL.trueCondition();
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
        Table<Record1<Long>> likeIdTable = dsl.select(GATHERING_LIKE.GATHERING_ID)
                .from(GATHERING_LIKE)
                .where(GATHERING_LIKE.USER_ID.eq(userId))
                .orderBy(GATHERING_LIKE.GATHERING_ID.asc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .asTable("gl");
        List<GatheringDto> contents = dsl.select(GATHERING.ID, GATHERING.THEME_ID, GATHERING.NAME, GATHERING.DATE, GATHERING.CAPACITY)
                .select(THEME.TITLE, THEME.PLAYTIME, THEME.LEVEL, THEME.IMAGE, SPOT.ADDRESS)
                .select(DSL.inline(true).as("isLiked"), DSL.count(GATHERING_MEMBER.USER_ID).as("participantCount"))
                .from(likeIdTable)
                .join(GATHERING).on(GATHERING.ID.eq(likeIdTable.field(GATHERING_LIKE.GATHERING_ID)))
                .join(THEME).on(GATHERING.THEME_ID.eq(THEME.ID))
                .join(SPOT).on(THEME.SPOT_ID.eq(SPOT.ID))
                .where(where)
                .groupBy(GATHERING.ID, GATHERING.THEME_ID, GATHERING.NAME, GATHERING.DATE, GATHERING.CAPACITY, THEME.TITLE, THEME.PLAYTIME, THEME.LEVEL, THEME.IMAGE, SPOT.ADDRESS)
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