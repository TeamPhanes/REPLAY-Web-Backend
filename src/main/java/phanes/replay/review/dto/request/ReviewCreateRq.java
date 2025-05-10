package phanes.replay.review.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class ReviewCreateRq {

    private Long themeId;
    private String content;
    private Double rating;
    @Pattern(regexp = "true|false")
    private String success;
    private List<MultipartFile> images;
    private Integer hint;
    private Integer numberOfPlayer;
    private String themeReview;
    private String storyReview;
    private String levelReview;
}
