package phanes.replay.theme.mapper;

import org.mapstruct.Mapper;
import phanes.replay.theme.dto.ThemeDetailDto;
import phanes.replay.theme.dto.ThemeDto;
import phanes.replay.theme.dto.ThemePreviewDto;
import phanes.replay.theme.dto.response.ThemeDetailRs;
import phanes.replay.theme.dto.response.ThemePreviewRs;
import phanes.replay.theme.dto.response.ThemeRs;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ThemeMapper {

    ThemePreviewRs toThemePreview(ThemePreviewDto themePreviewDto);

    ThemeRs toThemeRs(ThemeDto themeDto, Long reviewCount, Double avgScore, List<String> genres);

    ThemeDetailRs toThemeDetailRs(ThemeDetailDto detail, List<String> genres);
}