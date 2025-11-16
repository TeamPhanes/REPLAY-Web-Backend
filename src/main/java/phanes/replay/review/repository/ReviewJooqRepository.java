package phanes.replay.review.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import phanes.replay.review.domain.enums.Eval;
import phanes.replay.review.dto.response.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static phanes.replay.tables.Review.REVIEW;
import static phanes.replay.tables.ReviewLike.REVIEW_LIKE;
import static phanes.replay.tables.Users.USERS;

@Repository
@RequiredArgsConstructor
public class ReviewJooqRepository {

    private final DSLContext dsl;

    public Map<Long, Long> countAllByThemeIdList(List<Long> themeIdList) {
        Field<Long> reviewCount = DSL.count().cast(Long.class).as("reviewCount");
        return dsl.select(REVIEW.THEME_ID, reviewCount)
                .from(REVIEW)
                .where(REVIEW.THEME_ID.in(themeIdList))
                .groupBy(REVIEW.THEME_ID)
                .fetchMap(REVIEW.THEME_ID, reviewCount);
    }

    public Double aggregateByThemeId(Long themeId) {
        return Optional.ofNullable(
                dsl.select(REVIEW.THEME_ID, DSL.avg(REVIEW.SCORE).as("avgScore"))
                .from(REVIEW)
                .where(REVIEW.THEME_ID.eq(themeId))
                .groupBy(REVIEW.THEME_ID)
                .fetchOne("avgScore", Double.class)
        ).orElse(0.0);
    }

    public Map<Long, Double> aggregateAllByThemeIdList(List<Long> themeIdList) {
        Field<Double> avgScore = DSL.avg(REVIEW.SCORE).cast(Double.class).as("avgScore");
        return dsl.select(REVIEW.THEME_ID, avgScore)
                .from(REVIEW)
                .where(REVIEW.THEME_ID.in(themeIdList))
                .groupBy(REVIEW.THEME_ID)
                .fetchMap(REVIEW.THEME_ID, avgScore);
    }

    public List<ReviewDetailRs> findAllByThemeId(Long userId, Pageable pageable, Long themeId) {
        Field<Object> likeCount = DSL.selectCount()
                .from(REVIEW_LIKE)
                .where(REVIEW_LIKE.REVIEW_ID.eq(REVIEW.ID))
                .asField("likeCount");
        Field<Boolean> isLiked = DSL.exists(DSL
                        .selectOne()
                        .from(REVIEW_LIKE)
                        .where(REVIEW_LIKE.REVIEW_ID.eq(REVIEW.ID)
                                .and(REVIEW_LIKE.USER_ID.eq(userId))))
                .as("isLiked");
        return dsl.select(REVIEW.fields())
                .select(USERS.NICKNAME, USERS.PROFILE_IMAGE)
                .select(likeCount, isLiked)
                .from(REVIEW)
                .join(USERS).on(USERS.ID.eq(REVIEW.USER_ID))
                .where(REVIEW.THEME_ID.eq(themeId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchInto(ReviewDetailRs.class);
    }

    public UserEvaluation findEvaluationByThemeId(Long themeId) {
        return UserEvaluation.builder()
                .theme(fetchEvalPercent(REVIEW.THEME_REVIEW.cast(String.class), themeId))
                .level(fetchEvalPercent(REVIEW.LEVEL_REVIEW.cast(String.class), themeId))
                .story(fetchEvalPercent(REVIEW.STORY_REVIEW.cast(String.class), themeId))
                .build();
    }

    private Evaluation fetchEvalPercent(Field<String> evalField, Long themeId) {
        Result<Record2<String, Integer>> rows = dsl
                .select(evalField.as("eval"), DSL.count().as("count"))
                .from(REVIEW)
                .where(REVIEW.THEME_ID.eq(themeId))
                .groupBy(evalField)
                .fetch();
        if (rows.isEmpty()) {
            return Evaluation.emptyValue();
        }
        int total = rows.stream()
                .map(r -> r.get("count", Integer.class))
                .mapToInt(Integer::intValue)
                .sum();
        Record2<String, Integer> maxRow = rows.stream()
                .max(Comparator.comparingInt(r -> r.get("count", Integer.class)))
                .orElseThrow();
        Eval eval = Eval.valueOf(maxRow.get("eval", String.class));
        int max = maxRow.get("count", Integer.class);
        if (total == 0) {
            return Evaluation.emptyValue();
        }
        return Evaluation.builder()
                .label(eval)
                .percent((double) max * 100.0 / total)
                .build();
    }

    public ReviewCountSummary findScoreCountByThemeId(Long themeId) {
        Table<Record1<Integer>> SCORES = DSL.values(
                DSL.row(1),
                DSL.row(2),
                DSL.row(3),
                DSL.row(4),
                DSL.row(5)
        ).as("s", "score");
        Table<?> REVIEW_STAT = dsl
                .select(
                        DSL.round(REVIEW.SCORE).cast(Integer.class).as("score"),
                        DSL.count().as("cnt")
                )
                .from(REVIEW)
                .where(REVIEW.THEME_ID.eq(themeId))
                .groupBy(DSL.round(REVIEW.SCORE))
                .asTable("r");
        Field<Integer> S_SCORE = SCORES.field("score", Integer.class);
        Field<Integer> R_SCORE = REVIEW_STAT.field("score", Integer.class);
        Field<Long> R_CNT = REVIEW_STAT.field("cnt", Long.class);
        List<ReviewCountStat> counts = dsl.select(
                        S_SCORE.as("score"),
                        DSL.coalesce(R_CNT, 0L).as("count")
                )
                .from(SCORES)
                .leftJoin(REVIEW_STAT).on(R_SCORE.eq(S_SCORE))
                .orderBy(S_SCORE)
                .fetch()
                .stream()
                .map(record -> ReviewCountStat.builder()
                        .score(record.get("score", Integer.class))
                        .count(record.get("count", Long.class))
                        .build())
                .toList();
        Long total = dsl.selectCount()
                .from(REVIEW)
                .where(REVIEW.THEME_ID.eq(themeId))
                .fetchOneInto(Long.class);
        return ReviewCountSummary.builder()
                .total(total)
                .counts(counts)
                .build();
    }
}