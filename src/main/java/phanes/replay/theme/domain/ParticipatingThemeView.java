package phanes.replay.theme.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;

@Entity
@Immutable
@Getter
@Table(name = "participate_theme_summary")
public class ParticipatingThemeView {

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
    private BigDecimal rating;
    private Long reviewId;
    private Long reviewCount;
    private Integer score;
    private Integer hint;
    private Integer numberOfPlayer;
    private String themeReview;
    private String levelReview;
    private String storyReview;
    private String content;
    private Boolean success;
    private Boolean isLiked;
}
