package phanes.replay.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NaverProfileResponse {
    private String resultcode;
    private String message;
    private NaverProfile response;
}
