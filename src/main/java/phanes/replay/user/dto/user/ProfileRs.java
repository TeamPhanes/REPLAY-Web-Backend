package phanes.replay.user.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileRs {

    private String nickname;
    private String email;
    private String profileComment;
    private String profileImage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer createGatheringCount;
    private Integer visitGatheringCount;
    private Integer visitThemeCount;
    private Integer successThemeCount;
    private List<AchievementRs> achievements;
}