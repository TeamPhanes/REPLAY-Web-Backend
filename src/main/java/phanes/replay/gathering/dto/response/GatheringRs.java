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
@AllArgsConstructor
@NoArgsConstructor
public class GatheringRs {

    private Long gatheringId;
    private String name;
    private String address;
    private String cafe;
    private String spot;
    private String listImage;
    private Long themeId;
    private List<String> genres;
    private Integer playtime;
    private LocalDateTime dateTime;
    private LocalDateTime registrationEnd;
    private Integer capacity;
    private Integer participantCount;
    private Level level;
    private Boolean isLiked;
}