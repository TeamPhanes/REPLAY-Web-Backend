package phanes.replay.theme.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import phanes.replay.theme.dto.ThemeDetailDto;
import phanes.replay.theme.dto.ThemeDto;
import phanes.replay.theme.dto.ThemePreviewDto;
import phanes.replay.utils.JooqRepositoryUtils;

import java.util.*;

import static phanes.replay.tables.Cafe.CAFE;
import static phanes.replay.tables.Genre.GENRE;
import static phanes.replay.tables.Spot.SPOT;
import static phanes.replay.tables.Theme.THEME;
import static phanes.replay.tables.ThemeContent.THEME_CONTENT;
import static phanes.replay.tables.ThemeLike.THEME_LIKE;

@Repository
@RequiredArgsConstructor
public class ThemeJooqRepository {

    private final DSLContext dsl;
    private final JooqRepositoryUtils utils;

    public Page<ThemePreviewDto> findAllPreview(Pageable pageable, String genre) {
        Condition where = DSL.trueCondition();
        if (genre != null && !genre.isBlank()) {
            where = where.andExists(dsl.selectOne()
                    .from(GENRE)
                    .where(GENRE.THEME_ID.eq(THEME.ID).and(GENRE.NAME.eq(genre))));
        }
        Field<Integer> likeCount = DSL.count(THEME_LIKE.ID);
        List<ThemePreviewDto> contents = dsl
                .select(THEME.ID, THEME.TITLE, THEME.IMAGE)
                .from(THEME)
                .leftJoin(THEME_LIKE).on(THEME.ID.eq(THEME_LIKE.THEME_ID))
                .where(where)
                .groupBy(THEME.ID, THEME.TITLE, THEME.IMAGE)
                .orderBy(toSortFields(pageable, likeCount))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchInto(ThemePreviewDto.class);
        Long total = dsl
                .select(DSL.countDistinct(THEME.ID))
                .from(THEME)
                .leftJoin(THEME_LIKE).on(THEME.ID.eq(THEME_LIKE.THEME_ID))
                .where(where)
                .fetchOne(0, Long.class);
        return new PageImpl<>(contents, pageable, total == null ? 0 : total);
    }

    private SortField<?>[] toSortFields(Pageable pageable, Field<Integer> likeCount) {
        List<SortField<?>> sortFields = new ArrayList<>();
        for (Sort.Order order : pageable.getSort()) {
            String property = order.getProperty();
            boolean isAscending = order.isAscending();
            switch (property) {
                case "id" -> sortFields.add(isAscending ? THEME.ID.asc() : THEME.ID.desc());
                case "like" -> {
                    sortFields.add(isAscending ? likeCount.asc() : likeCount.desc());
                    sortFields.add(THEME.ID.asc());
                }
            }
        }
        if (sortFields.isEmpty()) {
            sortFields.add(THEME.ID.desc());
        }
        return sortFields.toArray(SortField[]::new);
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
        List<ThemeDto> themeDtoList = dsl
                .select(THEME.fields())
                .select(SPOT.NAME.as("spotName"), SPOT.ADDRESS, CAFE.NAME.as("cafeName"))
                .select(utils.isLikedTheme(userId), utils.isVisitedTheme(userId))
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

    public ThemeDetailDto findById(Long userId, Long themeId) {
        List<Field<?>> themeFieldList = Arrays.stream(THEME.fields())
                .filter(f -> !f.getName().equals("image"))
                .toList();
        return dsl.select(themeFieldList)
                .select(THEME_CONTENT.IMAGE, THEME_CONTENT.STORY, THEME_CONTENT.LINK)
                .select(SPOT.NAME.as("spotName"), SPOT.ADDRESS, SPOT.PHONE, CAFE.NAME.as("cafeName"))
                .select(utils.isLikedTheme(userId), utils.isVisitedTheme(userId))
                .from(THEME)
                .join(THEME_CONTENT).on(THEME_CONTENT.THEME_ID.eq(THEME.ID))
                .join(SPOT).on(SPOT.ID.eq(THEME.SPOT_ID))
                .join(CAFE).on(CAFE.ID.eq(SPOT.CAFE_ID))
                .where(THEME.ID.eq(themeId))
                .fetchOneInto(ThemeDetailDto.class);
    }

    public String findAddressById(Long id) {
        return dsl.select(SPOT.ADDRESS)
                .from(THEME)
                .join(SPOT).on(THEME.SPOT_ID.eq(SPOT.ID))
                .where(THEME.ID.eq(id))
                .fetchOneInto(String.class);
    }
}