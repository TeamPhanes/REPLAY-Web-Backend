package phanes.replay.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCommentDTO {

    private Long gatheringId;
    private String gatheringName;
    private String content;
    private LocalDateTime createdAt;
}
