package phanes.replay.theme.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Component;

import static phanes.replay.tables.Theme.THEME;
import static phanes.replay.tables.ThemeLike.THEME_LIKE;
import static phanes.replay.tables.ThemeVisit.THEME_VISIT;

@Component
@RequiredArgsConstructor
public class JooqRepositoryUtils {

    private final DSLContext dsl;

    public Field<Boolean> isLiked(Long userId) {
        return DSL.exists(dsl.selectOne()
                        .from(THEME_LIKE)
                        .where(THEME_LIKE.THEME_ID.eq(THEME.ID)
                                .and(THEME_LIKE.USER_ID.eq(userId))))
                .as("isLiked");
    }

    public Field<Boolean> isVisited(Long userId) {
        return DSL.exists(dsl.selectOne()
                        .from(THEME_VISIT)
                        .where(THEME_VISIT.THEME_ID.eq(THEME.ID)
                                .and(THEME_VISIT.USER_ID.eq(userId))))
                .as("isVisited");
    }
}