package phanes.replay.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRs {

    private Double avgScore;
    private Long createdGatheringCount;
    private List<ReviewCountStat> reviewCount;
    private UserEvaluation userEvaluation;
    private List<ReviewDetailRs> contents;
}