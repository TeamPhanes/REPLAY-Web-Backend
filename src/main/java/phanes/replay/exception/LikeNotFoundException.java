package phanes.replay.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class LikeNotFoundException extends RuntimeException {

    private final Map<String, Object> queryMaps;

    public LikeNotFoundException(String message, Map<String, Object> queryMaps) {
        super(message);
        this.queryMaps = queryMaps;
    }
}