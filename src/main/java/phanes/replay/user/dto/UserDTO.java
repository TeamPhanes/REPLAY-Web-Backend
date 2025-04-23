package phanes.replay.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String image;
    private String nickname;
    private String email;
    private String gender;
    private String comment;
    private Long totalGatherings;
    private Long totalMakeGatherings;
    private Long totalRE;
    private Long successCount;
    private Long failCount;
    private Boolean genderMark;
    private Boolean emailMark;
    private List<String> representAchievement;
}