package phanes.replay.utils;

import java.util.Map;
import java.util.stream.Collectors;

public class LogUtils {

    public static String toLogString(Map<String, Object> map) {
        return map.entrySet()
                .stream()
                .map(e -> e.getKey() + ": " + e.getValue())
                .collect(Collectors.joining(", "));
    }
}