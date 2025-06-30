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
public class ReviewRatingRs {

    private Long scoreCount;
    private Double averageScore;
    private List<Long> scores;
}