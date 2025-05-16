package phanes.replay.comment.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentRs {

    private Long commentId;
    private String nickname;
    private String image;
    private String content;
    private LocalDateTime createdAt;
    @Setter
    private List<ReCommentRs> reComments;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReCommentRs {
        private String reCommentId;
        private String nickname;
        private String image;
        private String content;
        private LocalDateTime createdAt;
    }
}
