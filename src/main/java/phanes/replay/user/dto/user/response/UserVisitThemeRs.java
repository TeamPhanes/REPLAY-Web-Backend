package phanes.replay.user.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVisitThemeRs {

    private Long themeId;
    private String address;
    private String spot;
    private String cafe;
    private String themeName;
    private String listImage;
    private String level;
    private List<String> genres;
    private Integer playtime;
    private Double rating;
    private Long reviewId;
    private Integer reviewCount;
    private Double myRating;
    private Integer hint;
    private Integer numberOfPlayer;
    private String themeReview;
    private String levelReview;
    private String storyReview;
    private String reviewComment;
    private Boolean success;
    private Boolean isLiked;
}
