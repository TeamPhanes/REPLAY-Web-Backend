package phanes.replay.gathering.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GatheringCommentRs {

    private Long id;
    private Long userId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String nickname;
    private String profileImage;
    private String email;
    @Setter
    private List<GatheringCommentRs> comments;
}