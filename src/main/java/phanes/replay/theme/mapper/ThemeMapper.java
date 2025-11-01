package phanes.replay.theme.mapper;

import org.mapstruct.Mapper;
import phanes.replay.theme.domain.Theme;
import phanes.replay.theme.dto.response.ThemePreviewRs;

@Mapper(componentModel = "spring")
public interface ThemeMapper {

    ThemePreviewRs toThemePreview(Theme theme);
}
