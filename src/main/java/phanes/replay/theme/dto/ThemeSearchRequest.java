package phanes.replay.theme.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThemeSearchRequest {
    private String keyword;
    private String genre;
    private String sortBy;
    private int limit = 10;
    private int offset = 0;
}
