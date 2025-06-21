package phanes.replay.review.dto.query;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewQuery {

    private Long id;
    private String content;
    private String image;
    private Boolean success;
    private Double score;
    private Integer hint;
    private Integer numberOfPlayer;
    private String themeReview;
    private String levelReview;
    private String storyReview;
    private Long likeCount;
    private Boolean isLiked;
    private String nickname;
    private String profileImage;
    private LocalDateTime createdAt;
}
