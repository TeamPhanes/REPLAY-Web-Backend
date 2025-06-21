package phanes.replay.theme.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThemeSearchRs {

    private Long themeId;
    private String themeName;
    private String cafe;
    private String spot;
}
