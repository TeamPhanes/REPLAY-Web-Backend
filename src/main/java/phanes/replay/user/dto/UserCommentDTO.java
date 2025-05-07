package phanes.replay.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCommentDTO {

    private Long gatheringId;
    private String gatheringName;
    private LocalTime time;
    private String content;
    private LocalDateTime createdAt;
}
