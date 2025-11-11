package phanes.replay.theme.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import phanes.replay.theme.domain.enums.Level;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThemeDto {

    private Long id;
    private Long spotId;
    private String title;
    private Integer playtime;
    private Level level;
    private String image;
    private Integer minPlayer;
    private Integer maxPlayer;
    private String note;
    private String cafeName;
    private String spotName;
    private String address;
    private Boolean isVisited;
    private Boolean isLiked;
}