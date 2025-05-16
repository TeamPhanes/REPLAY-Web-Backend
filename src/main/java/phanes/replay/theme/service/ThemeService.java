package phanes.replay.theme.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import phanes.replay.exception.ThemeNotFoundException;
import phanes.replay.theme.domain.Theme;
import phanes.replay.theme.domain.ThemeLikeView;
import phanes.replay.theme.dto.ThemeListResponse;
import phanes.replay.theme.dto.ThemeSearchRequest;
import phanes.replay.theme.repository.ThemeLikeViewRepository;
import phanes.replay.theme.repository.ThemeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ThemeService {

    private final ThemeLikeViewRepository themeLikeViewRepository;
    private final ThemeRepository themeRepository;

    public List<ThemeLikeView> getUserLikeTheme(Long userId, Pageable pageable) {
        return themeLikeViewRepository.findByUserId(userId, pageable);
    }

    public List<ThemeListResponse> getThemes(ThemeSearchRequest request, Long userId) {
        Pageable pageable = PageRequest.of(request.getOffset(), request.getLimit());
        return themeRepository.searchWithSort(
                request.getKeyword(),
                request.getGenre(),
                userId,
                request.getSortBy(),
                pageable
        );
    public Theme getTheme(Long themeId) {
        return themeRepository.findById(themeId).orElseThrow(() -> new ThemeNotFoundException("theme not found"));
    }
}
