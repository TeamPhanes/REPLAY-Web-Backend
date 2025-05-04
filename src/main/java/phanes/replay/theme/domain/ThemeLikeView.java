package phanes.replay.theme.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private String image;
    private Level level;
    private String genres;
    private Integer playtime;
}
