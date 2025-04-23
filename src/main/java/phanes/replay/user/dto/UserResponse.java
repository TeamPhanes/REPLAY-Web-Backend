package phanes.replay.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private String image;
    private String nickname;
    private String email;
    private String gender;
    private String comment;
    private Boolean genderMark;
    private Boolean emailMark;
}
