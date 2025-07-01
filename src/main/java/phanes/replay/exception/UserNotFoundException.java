package phanes.replay.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class UserNotFoundException extends RuntimeException {

    private final Map<String, Object> queryMaps;

    public UserNotFoundException(String message, Map<String, Object> queryMaps) {
        super(message);
        this.queryMaps = queryMaps;
    }
}