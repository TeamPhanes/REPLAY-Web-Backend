package phanes.replay.user.dto.user.query;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVisitThemeQuery {

    @Id
    private Long userId;
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
