package phanes.replay.theme.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import phanes.replay.theme.domain.enums.Level;

@Getter
@AllArgsConstructor
public class ThemeListResponse {
    private Long themeId;
    private String listImage;
    private String genre;
    private Integer playtime;
    private String themeName;
    private String spot;
    private Long reviewCount;
    private double rating;
    private boolean isLiked;
    private Level level;
    private String address;
}
