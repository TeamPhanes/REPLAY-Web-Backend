package phanes.replay.theme.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.Table;
import org.jooq.impl.DSL;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import phanes.replay.theme.dto.ThemeDto;

import java.util.List;

import static phanes.replay.tables.Cafe.CAFE;
import static phanes.replay.tables.Genre.GENRE;
import static phanes.replay.tables.Spot.SPOT;
import static phanes.replay.tables.Theme.THEME;
import static phanes.replay.tables.ThemeLike.THEME_LIKE;
import static phanes.replay.tables.ThemeVisit.THEME_VISIT;

@Repository
@RequiredArgsConstructor
public class ThemeVisitJooqRepository {

    private final DSLContext dsl;
    private final JooqRepositoryUtils utils;

    public Page<ThemeDto> findAllByVisit(Long userId, Pageable pageable, List<String> state, List<String> city, List<String> genres) {
        Condition where = DSL.trueCondition();
        if (state != null && !state.isEmpty()) {
            where = where.and(SPOT.STATE.in(state));
        }
        if (city != null && !city.isEmpty()) {
            where = where.and(SPOT.CITY.in(city));
        }
        if (genres != null && !genres.isEmpty()) {
            where = where.andExists(
                    dsl.selectOne()
                            .from(GENRE)
                            .where(GENRE.THEME_ID.eq(THEME.ID)
                                    .and(GENRE.NAME.in(genres))));
        }
        Table<Record1<Long>> visitIdTable = dsl
                .select(THEME_VISIT.THEME_ID)
                .from(THEME_VISIT)
                .where(THEME_VISIT.USER_ID.eq(userId))
                .orderBy(THEME_VISIT.THEME_ID.asc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .asTable("tl");
        List<ThemeDto> themeLikeDtoList = dsl
                .select(THEME.fields())
                .select(SPOT.NAME.as("spotName"), SPOT.ADDRESS, CAFE.NAME.as("cafeName"))
                .select(DSL.inline(true).as("isVisited"), utils.isLiked(userId))
                .from(visitIdTable)
                .join(THEME).on(THEME.ID.eq(THEME_VISIT.THEME_ID))
                .join(SPOT).on(THEME.SPOT_ID.eq(SPOT.ID))
                .join(CAFE).on(SPOT.CAFE_ID.eq(CAFE.ID))
                .where(where)
                .groupBy(THEME.ID)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchInto(ThemeDto.class);
        Long totalCount = dsl
                .selectCount()
                .from(THEME_LIKE)
                .where(THEME_LIKE.USER_ID.eq(userId))
                .fetchOne(0, Long.class);
        return new PageImpl<>(themeLikeDtoList, pageable, totalCount == null ? 0 : totalCount);
    }
}