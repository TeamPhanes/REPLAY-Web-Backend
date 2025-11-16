package phanes.replay.gathering.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import phanes.replay.theme.domain.enums.Level;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GatheringRs {

    private Long id;
    private String name;
    private String image;
    private LocalDateTime date;
    private Integer participantCount;
    private Integer capacity;
    private String cafeName;
    private String spotName;
    private String address;
    private List<String> genres;
    private Integer playtime;
    private Level level;
    private Boolean isLiked;
}