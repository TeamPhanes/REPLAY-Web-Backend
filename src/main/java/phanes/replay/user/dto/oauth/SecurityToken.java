package phanes.replay.user.dto.oauth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SecurityToken {

    private String accessToken;
    private String refreshToken;
}
