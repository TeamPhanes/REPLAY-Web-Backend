package phanes.replay.theme.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "participate_theme_summary")
public class ParticipatingThemeView {

    @Id
    private Long userId;
    private Long themeId;
    private String address;
    private String spot;
    private String cafe;
    private String name;
    private String image;
    private String level;
    private Integer playtime;
    private String genres;
    private Double totalRating;
    private Long reviewId;
    private Integer reviewCount;
    private Integer score;
    private Integer hint;
    private Integer numberOfPlayer;
    private String themeReview;
    private String levelReview;
    private String storyReview;
    private String content;
    private Boolean success;
}
