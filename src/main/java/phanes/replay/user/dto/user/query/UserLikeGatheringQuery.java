package phanes.replay.user.dto.user.query;

import lombok.Getter;
import lombok.Setter;
import phanes.replay.theme.domain.enums.Level;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserLikeGatheringQuery {

    private Long gatheringId;
    private String name;
    private String address;
    private String spot;
    private String cafe;
    private Long themeId;
    private String listImage;
    private String themeName;
    private LocalDateTime dateTime;
    private String genres;
    private Integer playtime;
    private Level level;
    private Integer capacity;
    private Integer participantCount;
    private LocalDateTime registrationEnd;
}
