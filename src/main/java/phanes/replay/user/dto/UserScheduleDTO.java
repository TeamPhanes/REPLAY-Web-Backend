package phanes.replay.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import phanes.replay.theme.domain.enums.Level;

import java.time.LocalTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserScheduleDTO {

    private Long gatheringId;
    private String name;
    private String address;
    private String spot;
    private String cafe;
    private String themeName;
    private String image;
    private List<String> genres;
    private Integer playtime;
    private Level level;
    private LocalTime time;
    private Integer capacity;
    private Integer participantCount;
    private Boolean isLiked;
}
