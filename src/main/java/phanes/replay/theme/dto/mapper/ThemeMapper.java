package phanes.replay.theme.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import phanes.replay.theme.domain.ThemeContent;
import phanes.replay.theme.dto.query.ThemeQuery;
import phanes.replay.theme.dto.response.ThemeDetailRs;
import phanes.replay.theme.dto.response.ThemeRs;

@Mapper(componentModel = "spring")
public interface ThemeMapper {

    @Mapping(target = "genres", expression = "java(java.util.Arrays.asList(themeQuery.getGenres().split(\",\")))")
    ThemeRs toThemeRs(ThemeQuery themeQuery);


    @Mapping(target = "themeId", source = "theme.id")
    @Mapping(target = "detailImage", source = "image")
    ThemeDetailRs toThemeDetailRs(ThemeContent themeContent);
}
