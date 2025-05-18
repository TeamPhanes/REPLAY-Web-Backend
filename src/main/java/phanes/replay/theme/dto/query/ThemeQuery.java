package phanes.replay.theme.dto.query;

import lombok.Getter;
import lombok.Setter;
import phanes.replay.theme.domain.enums.Level;

@Getter
@Setter
public class ThemeQuery {

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
    private Boolean isLiked;
    private Boolean isMarked;
}