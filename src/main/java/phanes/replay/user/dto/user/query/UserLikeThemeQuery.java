package phanes.replay.user.dto.user.query;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import phanes.replay.theme.domain.enums.Level;

@Getter
@Setter
public class UserLikeThemeQuery {

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
