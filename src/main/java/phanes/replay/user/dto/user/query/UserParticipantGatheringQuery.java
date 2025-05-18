package phanes.replay.user.dto.user.query;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import phanes.replay.theme.domain.enums.Level;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserParticipantGatheringQuery {

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
    private Level level;
    private Integer playtime;
    private LocalDateTime dateTime;
    private Integer capacity;
    private LocalDateTime registrationEnd;
    private Boolean isLiked;
}
