package phanes.replay.review.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewUpdateRq {

    private String content;
    private Double rating;
    private Integer hint;
    private Integer numberOfPlayer;
    private String themeReview;
    private String levelReview;
    private String storyReview;
    private Boolean success;
}