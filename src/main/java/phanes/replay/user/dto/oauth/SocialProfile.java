package phanes.replay.user.dto.oauth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import phanes.replay.user.domain.enums.SocialType;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocialProfile {

    private String socialId;
    private String profileImage;
    private SocialType socialType;
    private String gender;
    private String email;
}