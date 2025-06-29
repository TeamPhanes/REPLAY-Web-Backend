package phanes.replay.gathering.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GatheringMemberRs {

    private String image;
    private String nickname;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<String> representAchievement;
}