package phanes.replay.theme.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;
import phanes.replay.utils.JooqRepositoryUtils;

import java.util.List;

import static phanes.replay.tables.Review.REVIEW;
import static phanes.replay.tables.ThemeVisit.THEME_VISIT;

@Repository
@RequiredArgsConstructor
public class ThemeVisitJooqRepository {

    private final DSLContext dsl;
    private final JooqRepositoryUtils utils;

    public List<Boolean> findVisitByUserId(Long userId) {
        return dsl.select(DSL.coalesce(REVIEW.IS_SUCCESS, DSL.inline(false)))
                .from(THEME_VISIT)
                .leftJoin(REVIEW).on(THEME_VISIT.USER_ID.eq(REVIEW.USER_ID)
                        .and(THEME_VISIT.THEME_ID.eq(REVIEW.THEME_ID)))
                .where(THEME_VISIT.USER_ID.eq(userId))
                .fetchInto(Boolean.class);
    }
}