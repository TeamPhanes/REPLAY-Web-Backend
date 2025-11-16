package phanes.replay.theme.dto.response;

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
public class ThemeDetailRs {

    private Long id;
    private String title;
    private String story;
    private Integer playtime;
    private Level level;
    private String image;
    private Integer minPlayer;
    private Integer maxPlayer;
    private String note;
    private String cafeName;
    private String spotName;
    private String address;
    private String phone;
    private String link;
    private List<String> genres;
    private Boolean isVisited;
    private Boolean isLiked;
}