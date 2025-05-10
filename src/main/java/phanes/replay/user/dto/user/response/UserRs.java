package phanes.replay.user.dto.user.response;

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
public class UserRs {

    private String image;
    private String nickname;
    private String gender;
    private String email;
    private String comment;
    private Boolean genderMark;
    private Boolean emailMark;
    private Long totalGathering;
    private Long totalMakeGathering;
    private Long totalTheme;
    private Long successCount;
    private Long failCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<String> representAchievement;
}
