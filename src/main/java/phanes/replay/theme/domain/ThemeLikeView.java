package phanes.replay.theme.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Immutable;
import phanes.replay.theme.domain.enums.Level;

@Entity
@Getter
@Immutable
@Table(name = "like_theme_summary")
public class ThemeLikeView {

    @Id
    private Long userId;
    private Long themeId;
    private String address;
    private String spot;
    private String cafe;
    private String themeName;
    private String listImage;
    @Enumerated(EnumType.STRING)
    private Level level;
    private String genres;
    private Integer playtime;
    private Long reviewCount;
    private Double rating;
    private Boolean isMarked;
}
