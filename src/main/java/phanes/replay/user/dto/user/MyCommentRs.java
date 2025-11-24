package phanes.replay.user.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyCommentRs {

    private String nickname;
    private String content;
    private Long gatheringId;
    private LocalDateTime createdAt;
}