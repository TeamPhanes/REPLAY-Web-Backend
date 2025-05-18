package phanes.replay.user.dto.user.query;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import phanes.replay.theme.domain.enums.Level;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserSchedule {

    @Id
    private Long userId;
    private Long gatheringId;
    private String name;
    private String address;
    private String spot;
    private String cafe;
    private Long themeId;
    private String themeName;
    private String listImage;
    private String genres;
    @Enumerated(EnumType.STRING)
    private Level level;
    private Integer playtime;
    private LocalDateTime dateTime;
    private Integer capacity;
    private Integer participantCount;
    private Boolean isLiked;
}
