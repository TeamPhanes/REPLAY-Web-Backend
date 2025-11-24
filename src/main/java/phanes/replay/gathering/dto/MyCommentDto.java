package phanes.replay.gathering.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyCommentDto {

    private String content;
    private Long gatheringId;
    private LocalDateTime createdAt;
}