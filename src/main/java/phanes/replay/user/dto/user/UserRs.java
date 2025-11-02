package phanes.replay.user.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserRs {

    private Long nickname;
    private String image;
}