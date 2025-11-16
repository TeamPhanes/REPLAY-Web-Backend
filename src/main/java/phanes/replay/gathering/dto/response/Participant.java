package phanes.replay.gathering.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Participant {

    private Long id;
    private String profileImage;
    private String nickname;
    private String email;
}