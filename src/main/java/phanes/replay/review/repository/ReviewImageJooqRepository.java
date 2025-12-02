package phanes.replay.review.repository;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static phanes.replay.tables.ReviewImage.REVIEW_IMAGE;

@Repository
@RequiredArgsConstructor
public class ReviewImageJooqRepository {

    private final DSLContext dsl;

    public Map<Long, List<String>> findAllByReviewIdList(List<Long> reviewIdList) {
        return dsl.select(REVIEW_IMAGE.REVIEW_ID, REVIEW_IMAGE.IMAGE)
                .from(REVIEW_IMAGE)
                .where(REVIEW_IMAGE.REVIEW_ID.in(reviewIdList))
                .orderBy(REVIEW_IMAGE.IS_REPRESENTATIVE.desc(), REVIEW_IMAGE.ID.asc())
                .fetchGroups(REVIEW_IMAGE.REVIEW_ID, REVIEW_IMAGE.IMAGE);
    }
}