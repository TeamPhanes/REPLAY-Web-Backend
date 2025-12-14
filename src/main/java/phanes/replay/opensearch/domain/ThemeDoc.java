package phanes.replay.opensearch.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import phanes.replay.theme.domain.enums.Level;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThemeDoc {

    private Long id;
    private String title;
    private Integer playtime;
    private Level level;
    private SpotDoc spot;
    private CafeDoc cafe;
    private List<String> genres;
}