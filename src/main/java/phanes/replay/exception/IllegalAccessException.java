package phanes.replay.exception;

import lombok.Getter;

@Getter
public class IllegalAccessException extends RuntimeException {

    private final Long userId;
    private final Long hostId;

    public IllegalAccessException(String message, Long userId, Long hostId) {
        super(message);
        this.userId = userId;
        this.hostId = hostId;
    }
}