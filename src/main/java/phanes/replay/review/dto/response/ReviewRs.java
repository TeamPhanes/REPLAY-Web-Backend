package phanes.replay.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRs {

    private Double avgScore;
    private Long createdGatheringCount;
    private ReviewCountSummary reviewCountSummary;
    private UserEvaluation userEvaluation;
    private Page<ReviewDetailRs> contents;
}