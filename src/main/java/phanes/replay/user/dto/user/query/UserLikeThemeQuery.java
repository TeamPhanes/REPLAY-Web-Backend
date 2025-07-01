package phanes.replay.user.dto.user.query;

import lombok.Getter;
import lombok.Setter;
import phanes.replay.theme.domain.enums.Level;

@Getter
@Setter
public class UserLikeThemeQuery {

    private Long themeId;
    private String address;
    private String spot;
    private String cafe;
    private String themeName;
    private String listImage;
    private Level level;
    private String genres;
    private Integer playtime;
    private Long reviewCount;
    private Double rating;
    private Boolean isMarked;
}