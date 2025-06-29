package phanes.replay.exception;

import lombok.Getter;

@Getter
public class GatheringNotFoundException extends RuntimeException {

    private final Long gatheringId;

    public GatheringNotFoundException(String message, Long gatheringId) {
        super(message);
        this.gatheringId = gatheringId;
    }
}