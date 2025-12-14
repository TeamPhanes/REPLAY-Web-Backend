package phanes.replay.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThemeSuggestDoc {

    private Long id;
    private String title;
    private SpotDoc spot;
}