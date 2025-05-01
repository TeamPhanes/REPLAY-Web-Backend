package phanes.replay.user.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String image;
    private String nickname;
    @Setter
    private String gender;
    @Setter
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
