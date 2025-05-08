package phanes.replay.user.dto;

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
public class UserLikeGatheringDTO {

    private Long gatheringId;
    private String name;
    private String address;
    private String spot;
    private String cafe;
    private Long themeId;
    private String listImage;
    private String themeName;
    private List<String> genres;
    private Integer playtime;
    private Integer capacity;
    private Level level;
    private LocalDateTime dateTime;
    private Integer participantCount;
    private LocalDateTime registrationEnd;
}
