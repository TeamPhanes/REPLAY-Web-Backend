package phanes.replay.theme.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThemeDetailRs {

    private Long themeId;
    private String detailImage;
    private String story;
}
