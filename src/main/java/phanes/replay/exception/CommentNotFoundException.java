package phanes.replay.exception;

import lombok.Getter;

@Getter
public class CommentNotFoundException extends RuntimeException {

    private final Long userId;
    private final Long gatheringId;
    private final Long commentId;

    public CommentNotFoundException(String message, Long userId, Long gatheringId, Long commentId) {
        super(message);
        this.userId = userId;
        this.gatheringId = gatheringId;
        this.commentId = commentId;
    }
}