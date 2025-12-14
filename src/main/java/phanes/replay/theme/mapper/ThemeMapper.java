package phanes.replay.theme.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import phanes.replay.opensearch.domain.ThemeDoc;
import phanes.replay.opensearch.domain.ThemeSuggestDoc;
import phanes.replay.theme.dto.ThemeDetailDto;
import phanes.replay.theme.dto.ThemeDto;
import phanes.replay.theme.dto.ThemePreviewDto;
import phanes.replay.theme.dto.response.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ThemeMapper {

    ThemePreviewRs toThemePreview(ThemePreviewDto themePreviewDto);

    ThemeRs toThemeRs(ThemeDto themeDto, Long reviewCount, Double avgScore, List<String> genres);

    ThemeDetailRs toThemeDetailRs(ThemeDetailDto detail, List<String> genres);

    @Mapping(source = "spot.name", target = "spotName")
    ThemeSuggestRs toThemeSuggestRs(ThemeSuggestDoc themeSuggestDoc);

    @Mapping(source = "themeDoc.id", target = "id")
    @Mapping(source = "themeDoc.title", target = "title")
    @Mapping(source = "themeDoc.playtime", target = "playtime")
    @Mapping(source = "themeDoc.level", target = "level")
    @Mapping(source = "themeDoc.spot.name", target = "spotName")
    @Mapping(source = "themeDoc.cafe.name", target = "cafeName")
    @Mapping(source = "themeDoc.spot.address", target = "address")
    @Mapping(source = "themeDoc.genres", target = "genres")
    ThemeSearchRs toThemeSearchRs(ThemeDoc themeDoc, ThemeDto themeDto, Long reviewCount, Double avgScore);
}