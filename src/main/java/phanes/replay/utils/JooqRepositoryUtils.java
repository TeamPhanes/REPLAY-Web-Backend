package phanes.replay.utils;

import org.jooq.Field;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Component;

import static phanes.replay.tables.Gathering.GATHERING;
import static phanes.replay.tables.GatheringLike.GATHERING_LIKE;
import static phanes.replay.tables.Theme.THEME;
import static phanes.replay.tables.ThemeLike.THEME_LIKE;
import static phanes.replay.tables.ThemeVisit.THEME_VISIT;

@Component
public class JooqRepositoryUtils {

    public Field<Boolean> isLikedTheme(Long userId) {
        return DSL.exists(DSL.selectOne()
                        .from(THEME_LIKE)
                        .where(THEME_LIKE.THEME_ID.eq(THEME.ID)
                                .and(THEME_LIKE.USER_ID.eq(userId))))
                .as("isLiked");
    }

    public Field<Boolean> isVisitedTheme(Long userId) {
        return DSL.exists(DSL.selectOne()
                        .from(THEME_VISIT)
                        .where(THEME_VISIT.THEME_ID.eq(THEME.ID)
                                .and(THEME_VISIT.USER_ID.eq(userId))))
                .as("isVisited");
    }

    public Field<Boolean> isLikedGathering(Long userId) {
        return DSL.exists(DSL.selectOne()
                        .from(GATHERING_LIKE)
                        .where(GATHERING_LIKE.GATHERING_ID.eq(GATHERING.ID)
                                .and(GATHERING_LIKE.USER_ID.eq(userId))))
                .as("isLiked");
    }
}