package phanes.replay.review.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import phanes.replay.review.domain.enums.Eval;
import phanes.replay.review.dto.response.Evaluation;
import phanes.replay.review.dto.response.ReviewCountStat;
import phanes.replay.review.dto.response.ReviewDetailRs;
import phanes.replay.review.dto.response.UserEvaluation;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static phanes.replay.tables.Review.REVIEW;
import static phanes.replay.tables.Users.USERS;

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
        return dsl.select(REVIEW.THEME_ID, DSL.avg(REVIEW.SCORE).as("avgScore"))
                .from(REVIEW)
                .where(REVIEW.THEME_ID.in(themeIdList))
                .groupBy(REVIEW.THEME_ID)
                .fetchMap(REVIEW.THEME_ID, DSL.field("avgScore").cast(Double.class));
    }

    public List<ReviewDetailRs> findAllByThemeId(Long userId, Pageable pageable, Long themeId) {
        Field<Object> likeCount = DSL.selectCount()
                .from(REVIEW)
                .where(REVIEW.THEME_ID.eq(themeId))
                .asField("likeCount");
        Field<Boolean> isLiked = DSL.exists(dsl
                        .selectOne()
                        .from(REVIEW)
                        .where(REVIEW.THEME_ID.eq(themeId)
                                .and(REVIEW.USER_ID.eq(userId))))
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
                .theme(fetchEvalPercent(REVIEW.THEME_REVIEW.cast(Eval.class), themeId))
                .level(fetchEvalPercent(REVIEW.LEVEL_REVIEW.cast(Eval.class), themeId))
                .story(fetchEvalPercent(REVIEW.STORY_REVIEW.cast(Eval.class), themeId))
                .build();
    }

    private Evaluation fetchEvalPercent(Field<Eval> evalField, Long themeId) {
        Result<Record2<Eval, Integer>> rows = dsl
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
        Record2<Eval, Integer> maxRow = rows.stream()
                .max(Comparator.comparingInt(r -> r.get("count", Integer.class)))
                .orElseThrow();
        Eval eval = maxRow.get("eval", Eval.class);
        int max = maxRow.get("count", Integer.class);
        if (total == 0) {
            return Evaluation.emptyValue();
        }
        return Evaluation.builder()
                .label(eval)
                .percent((double) max * 100.0 / total)
                .build();
    }

    public List<ReviewCountStat> findScoreCountByThemeId(Long themeId) {
        return dsl.select(DSL.round(REVIEW.SCORE).as("score"), DSL.count().as("count"))
                .from(REVIEW)
                .where(REVIEW.THEME_ID.eq(themeId))
                .groupBy(DSL.round(REVIEW.SCORE))
                .orderBy(DSL.round(REVIEW.SCORE))
                .fetch()
                .stream()
                .map(record -> ReviewCountStat.builder()
                        .reviewScore(record.get("score", Integer.class))
                        .count(record.get("count", Long.class))
                        .build())
                .toList();
    }
}