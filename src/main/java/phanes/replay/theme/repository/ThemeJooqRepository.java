package phanes.replay.theme.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import phanes.replay.theme.domain.Theme;
import phanes.replay.theme.dto.ThemeDto;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static phanes.replay.tables.Cafe.CAFE;
import static phanes.replay.tables.Genre.GENRE;
import static phanes.replay.tables.Spot.SPOT;
import static phanes.replay.tables.Theme.THEME;
import static phanes.replay.tables.ThemeLike.THEME_LIKE;

@Repository
@RequiredArgsConstructor
public class ThemeJooqRepository {

    private final DSLContext dsl;
    private final JooqRepositoryUtils utils;

    public List<Theme> findAllOrderByThemeLike(int size) {
        Table<Record2<Long, Integer>> likeCountByThemeId = likeCountByThemeId();
        Field<Long> likeCount = DSL.coalesce(DSL.field(DSL.name("tlc", "lc"), Long.class), DSL.inline(0L)).as("like_count");
        Field<Long> themeId = DSL.field(DSL.name("tlc", "theme_id"), Long.class);
        List<SortField<?>> orderBy = List.of(likeCount.desc(), THEME.ID.asc());
        return dsl.select(THEME.fields())
                .select(likeCount)
                .from(THEME)
                .leftJoin(likeCountByThemeId).on(themeId.eq(THEME.ID))
                .orderBy(orderBy)
                .limit(size)
                .fetchInto(Theme.class);
    }

    private Table<Record2<Long, Integer>> likeCountByThemeId() {
        return dsl.select(THEME_LIKE.THEME_ID, DSL.count().as("lc"))
                .from(THEME_LIKE)
                .groupBy(THEME_LIKE.THEME_ID)
                .asTable("tlc");
    }

    public Page<ThemeDto> findAll(Long userId, Pageable pageable, List<String> locations, List<String> genres) {
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
            if(!pairs.isEmpty()) {
                where = where.and(DSL.row(SPOT.STATE, SPOT.CITY).in(pairs));
            }
            if(!stateOnly.isEmpty()) {
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
        List<ThemeDto> themeDtoList = dsl
                .select(THEME.fields())
                .select(SPOT.NAME.as("spotName"), SPOT.ADDRESS, CAFE.NAME.as("cafeName"))
                .select(utils.isLiked(userId), utils.isVisited(userId))
                .from(THEME)
                .join(SPOT).on(THEME.SPOT_ID.eq(SPOT.ID))
                .join(CAFE).on(SPOT.CAFE_ID.eq(CAFE.ID))
                .where(where)
                .groupBy(THEME.ID)
                .orderBy(THEME.ID.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchInto(ThemeDto.class);
        Long totalCount = dsl.selectCount()
                .from(THEME)
                .join(SPOT).on(THEME.SPOT_ID.eq(SPOT.ID))
                .where(where)
                .fetchOne(0, Long.class);
        return new PageImpl<>(themeDtoList, pageable, totalCount == null ? 0 : totalCount);
    }
}