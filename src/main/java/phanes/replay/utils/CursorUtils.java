package phanes.replay.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import phanes.replay.common.dto.response.Cursor;

import java.util.Base64;

public class CursorUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static Cursor decode(String cursor) {
        try {
            byte[] bytes = Base64.getUrlDecoder().decode(cursor);
            return mapper.readValue(bytes, Cursor.class);
        } catch (Exception e) {
            throw new RuntimeException("cursor decode fail", e);
        }
    }
}