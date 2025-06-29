package phanes.replay.exception;

import lombok.Getter;

@Getter
public class GatheringContentNotFoundException extends RuntimeException {

    private final Long gatheringId;

    public GatheringContentNotFoundException(String message, Long gatheringId) {
        super(message);
        this.gatheringId = gatheringId;
    }
}