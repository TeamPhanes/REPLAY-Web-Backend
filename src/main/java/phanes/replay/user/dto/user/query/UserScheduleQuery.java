package phanes.replay.user.dto.user.query;

import lombok.Getter;
import lombok.Setter;
import phanes.replay.theme.domain.enums.Level;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserScheduleQuery {

    private Long gatheringId;
    private String name;
    private String address;
    private String spot;
    private String cafe;
    private Long themeId;
    private String themeName;
    private String listImage;
    private String genres;
    private Level level;
    private Integer playtime;
    private LocalDateTime dateTime;
    private Integer capacity;
    private Integer participantCount;
    private Boolean isLiked;
}
