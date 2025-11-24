package phanes.replay.user.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AchievementRs {

    private Long id;
    private String name;
    private String image;
    private Boolean isRepresentative;
}