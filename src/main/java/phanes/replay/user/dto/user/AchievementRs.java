package phanes.replay.user.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AchievementRs {

    private Long id;
    private Integer progress;
    private Boolean isRepresentative;
    private LocalDateTime completedAt;
}