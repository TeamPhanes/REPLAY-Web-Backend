package phanes.replay.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewSummary {

    private Double avgScore;
    private Long createdGatheringCount;
    private ReviewCountSummary reviewCountSummary;
    private UserEvaluation userEvaluation;
}
