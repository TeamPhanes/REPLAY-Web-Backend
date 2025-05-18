package phanes.replay.user.dto.user.query;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserVisitThemeQuery {

    private Long themeId;
    private String address;
    private String spot;
    private String cafe;
    private String name;
    private String listImage;
    private String level;
    private Integer playtime;
    private String genres;
    private Double rating;
    private Long reviewId;
    private Long reviewCount;
    private Double score;
    private Integer hint;
    private Integer numberOfPlayer;
    private String themeReview;
    private String levelReview;
    private String storyReview;
    private String content;
    private Boolean success;
    private Boolean isLiked;
}
