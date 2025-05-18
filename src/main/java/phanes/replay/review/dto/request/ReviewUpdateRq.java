package phanes.replay.review.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ReviewUpdateRq {

    private Long id;
    private MultipartFile image;
    private Double myRating;
    private Integer hint;
    private Integer numberOfPlayer;
    private String themeReview;
    private String levelReview;
    private String storyReview;
    private String reviewComment;
    private Boolean success;
}
