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
public class UserLikeGatheringQuery {

    @Id
    private Long userId;
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
    @Enumerated(EnumType.STRING)
    private Level level;
    private Integer capacity;
    private Integer participantCount;
    private LocalDateTime registrationEnd;
}
