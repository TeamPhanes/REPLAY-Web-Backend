package phanes.replay.user.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserRs {

    private String nickname;
    private String image;
}