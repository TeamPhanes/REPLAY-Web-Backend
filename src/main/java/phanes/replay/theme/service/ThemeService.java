package phanes.replay.theme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import phanes.replay.theme.dto.mapper.ThemeMapper;
import phanes.replay.theme.dto.response.ThemeDetailRs;
import phanes.replay.theme.dto.response.ThemeRs;
import phanes.replay.theme.persistence.mapper.ThemeQueryMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ThemeService {

    private final ThemeContentQueryService themeContentQueryService;
    private final ThemeQueryMapper themeQueryMapper;
    private final ThemeMapper themeMapper;

    public List<ThemeRs> getThemeList(Long userId, String sortBy, String keyword, String city, String state, String genre, Integer limit, Integer offset) {
        return themeQueryMapper.findAllByKeywordAndAddress(userId, sortBy, keyword, city, state, genre, limit, offset).stream()
                .map(themeMapper::toThemeRs).toList();
    }

    public ThemeDetailRs getThemeDetail(Long themeId) {
        return themeMapper.toThemeDetailRs(themeContentQueryService.findById(themeId));
    }
}
