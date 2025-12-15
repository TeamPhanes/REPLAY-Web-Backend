package phanes.replay.review.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import phanes.replay.review.domain.enums.Eval;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRq {

    private Double score;
    private Eval themeReview;
    private Eval levelReview;
    private Eval storyReview;
    private Boolean isSuccess;
    private Integer numberOfPlayer;
    private Integer hint;
    private String content;
    private LocalDateTime date;
    private String representativeId;
}