package phanes.replay.user.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import phanes.replay.theme.domain.enums.Level;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLikeThemeRs {

    private Long themeId;
    private String address;
    private String spot;
    private String cafe;
    private String themeName;
    private String listImage;
    private Level level;
    private List<String> genres;
    private Integer playtime;
    private Long reviewCount;
    private Double rating;
    private Boolean isMarked;
}