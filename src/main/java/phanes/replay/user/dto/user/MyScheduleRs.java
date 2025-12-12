package phanes.replay.user.dto.user;

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
public class MyScheduleRs {

    private Long id;
    private String name;
    private Integer participantCount;
    private Integer capacity;
    private LocalDateTime date;
    private String title;
    private String image;
    private Integer playtime;
    private Level level;
    private String address;
    private List<String> genres;
    private Boolean isLiked;
}