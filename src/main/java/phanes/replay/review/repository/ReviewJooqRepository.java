package phanes.replay.review.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static phanes.replay.tables.Review.REVIEW;

@Repository
@RequiredArgsConstructor
public class ReviewJooqRepository {

    private final DSLContext dsl;

    public Map<Long, Long> countAllByThemeIdList(List<Long> themeIdList) {
        return dsl.select(REVIEW.THEME_ID, DSL.count().as("reviewCount"))
                .from(REVIEW)
                .where(REVIEW.ID.in(themeIdList))
                .groupBy(REVIEW.THEME_ID)
                .fetchMap(REVIEW.THEME_ID, DSL.field("reviewCount").cast(Long.class));
    }

    public Map<Long, Double> aggregateAllByThemeIdList(List<Long> themeIdList) {
        return dsl.select(REVIEW.THEME_ID, DSL.avg(REVIEW.SCORE).as("avgScore"))
                .from(REVIEW)
                .where(REVIEW.THEME_ID.in(themeIdList))
                .groupBy(REVIEW.THEME_ID)
                .fetchMap(REVIEW.THEME_ID, DSL.field("avgScore").cast(Double.class));
    }
}