package phanes.replay.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class TimeUtils {

    public static String toTimeStringWithKST(LocalDateTime time){
        return time.atZone(ZoneId.of("Asia/Seoul")).toOffsetDateTime().toString();
    }
}