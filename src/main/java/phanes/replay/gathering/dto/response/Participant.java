package phanes.replay.gathering.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import phanes.replay.gathering.domain.enums.Role;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Participant {

    private Long id;
    private String profileImage;
    private String nickname;
    private String email;
    private Role role;
}