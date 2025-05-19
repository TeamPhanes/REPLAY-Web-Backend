package phanes.replay.gathering.dto.query;

import lombok.Getter;
import lombok.Setter;
import phanes.replay.theme.domain.enums.Level;

import java.time.LocalDateTime;

@Getter
@Setter
public class GatheringQuery {

    private Long gatheringId;
    private String name;
    private String listImage;
    private String genres;
    private Integer playtime;
    private String address;
    private String cafe;
    private String spot;
    private Level level;
    private LocalDateTime dateTime;
    private LocalDateTime registrationEnd;
    private Integer capacity;
    private Integer participantCount;
    private Boolean isLiked;
}
