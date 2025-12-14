package phanes.replay.theme.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import phanes.replay.common.dto.response.ThemeSuggestDoc;
import phanes.replay.theme.dto.ThemeDetailDto;
import phanes.replay.theme.dto.ThemeDto;
import phanes.replay.theme.dto.ThemePreviewDto;
import phanes.replay.theme.dto.response.ThemeDetailRs;
import phanes.replay.theme.dto.response.ThemePreviewRs;
import phanes.replay.theme.dto.response.ThemeRs;
import phanes.replay.theme.dto.response.ThemeSuggestRs;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ThemeMapper {

    ThemePreviewRs toThemePreview(ThemePreviewDto themePreviewDto);

    ThemeRs toThemeRs(ThemeDto themeDto, Long reviewCount, Double avgScore, List<String> genres);

    ThemeDetailRs toThemeDetailRs(ThemeDetailDto detail, List<String> genres);

    @Mapping(source = "spot.name", target = "spotName")
    ThemeSuggestRs toThemeSuggestRs(ThemeSuggestDoc themeSuggestDoc);
}