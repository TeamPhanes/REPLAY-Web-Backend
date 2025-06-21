package phanes.replay.review.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ReviewUpdateRq {

    private String content;
    private MultipartFile image;
    private Double rating;
    private Integer hint;
    private Integer numberOfPlayer;
    private String themeReview;
    private String levelReview;
    private String storyReview;
    @Pattern(regexp = "true|false")
    private Boolean success;
}
