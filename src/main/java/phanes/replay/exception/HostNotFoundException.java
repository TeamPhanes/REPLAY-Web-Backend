package phanes.replay.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class HostNotFoundException extends RuntimeException {

    private final Map<String, Object> queryMaps;

    public HostNotFoundException(String message, Map<String, Object> queryMaps) {
        super(message);
        this.queryMaps = queryMaps;
    }
}