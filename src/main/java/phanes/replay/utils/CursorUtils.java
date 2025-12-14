package phanes.replay.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.opensearch.client.opensearch._types.FieldValue;
import org.opensearch.client.opensearch.core.search.Hit;
import phanes.replay.opensearch.dto.response.Cursor;

import java.util.Base64;
import java.util.List;

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

    public static <T> Cursor getNextCursor(List<Hit<T>> hits, Integer size) {
        if (!hits.isEmpty() && hits.size() == size) {
            Hit<T> lastHit = hits.getLast();
            List<FieldValue> sortValues = lastHit.sort();
            if (sortValues.size() == 2) {
                FieldValue scoreValue = sortValues.get(0);
                Double score = scoreValue.doubleValue();
                FieldValue idValue = sortValues.get(1);
                String id = idValue.stringValue();
                return new Cursor(id, score);
            }
        }
        return null;
    }
}