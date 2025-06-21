package phanes.replay.theme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import phanes.replay.common.dto.mapper.PageMapper;
import phanes.replay.common.dto.response.Page;
import phanes.replay.theme.domain.Theme;
import phanes.replay.theme.domain.ThemeLike;
import phanes.replay.theme.domain.ThemeVisit;
import phanes.replay.theme.dto.mapper.ThemeMapper;
import phanes.replay.theme.dto.response.ThemeDetailRs;
import phanes.replay.theme.dto.response.ThemeRs;
import phanes.replay.theme.dto.response.ThemeSearchRs;
import phanes.replay.theme.persistence.mapper.ThemeQueryMapper;
import phanes.replay.user.domain.User;
import phanes.replay.user.service.UserQueryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ThemeService {

    private final ThemeContentQueryService themeContentQueryService;
    private final ThemeVisitQueryService themeVisitQueryService;
    private final ThemeLikeQueryService themeLikeQueryService;
    private final PageMapper<List<ThemeRs>> pageMapper;
    private final ThemeQueryService themeQueryService;
    private final UserQueryService userQueryService;
    private final ThemeQueryMapper themeQueryMapper;
    private final ThemeMapper themeMapper;

    public Page<List<ThemeRs>> getThemeList(Long userId, String sortBy, String keyword, String city, String state, String genre, Integer limit, Integer offset) {
        Long totalCount = themeQueryMapper.countByKeywordAndAddress(keyword, city, state, genre);
        List<ThemeRs> data = themeQueryMapper.findAllByKeywordAndAddress(userId, sortBy, keyword, city, state, genre, limit, offset)
                .stream()
                .map(themeMapper::toThemeRs)
                .toList();
        return pageMapper.toPage(totalCount, offset, data);
    }

    public ThemeDetailRs getThemeDetail(Long themeId) {
        return themeMapper.toThemeDetailRs(themeContentQueryService.findByThemeId(themeId));
    }

    public List<ThemeSearchRs> getThemeSearchList(String keyword, String city, String state) {
        return themeQueryMapper.findAllByKeywordAndCityAndState(keyword, city, state)
                .stream()
                .map(themeMapper::toThemeSearchRs)
                .toList();
    }

    public void updateThemeLike(Long userId, Long themeId) {
        User user = userQueryService.findById(userId);
        Theme theme = themeQueryService.findById(themeId);
        ThemeLike themeLike = ThemeLike.builder().user(user).theme(theme).build();
        themeLikeQueryService.save(themeLike);
    }

    public void updateThemeVisit(Long userId, Long themeId) {
        User user = userQueryService.findById(userId);
        Theme theme = themeQueryService.findById(themeId);
        ThemeVisit themeVisit = ThemeVisit.builder().user(user).theme(theme).build();
        themeVisitQueryService.save(themeVisit);
    }

    public void deleteThemeLike(Long userId, Long themeId) {
        ThemeLike themeLike = themeLikeQueryService.findByUserIdAndThemeId(userId, themeId);
        themeLikeQueryService.delete(themeLike);
    }

    public void deleteThemeVisit(Long userId, Long themeId) {
        ThemeVisit themeVisit = themeVisitQueryService.findByUserIdAndThemeId(userId, themeId);
        themeVisitQueryService.delete(themeVisit);
    }
}
