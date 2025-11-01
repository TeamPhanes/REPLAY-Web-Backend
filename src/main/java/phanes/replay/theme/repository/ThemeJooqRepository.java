package phanes.replay.theme.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;
import phanes.replay.theme.domain.Theme;
import phanes.replay.theme.mapper.ThemeRecordMapper;

import java.util.List;

import static phanes.replay.tables.Theme.THEME;
import static phanes.replay.tables.ThemeLike.THEME_LIKE;

@Repository
@RequiredArgsConstructor
public class ThemeJooqRepository {

    private final DSLContext dsl;

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
                .fetch(ThemeRecordMapper.themeRecordMapper());
    }

    private Table<Record2<Long, Integer>> likeCountByThemeId() {
        return dsl.select(THEME_LIKE.THEME_ID, DSL.count().as("lc"))
                .from(THEME_LIKE)
                .groupBy(THEME_LIKE.THEME_ID)
                .asTable("tlc");
    }
}