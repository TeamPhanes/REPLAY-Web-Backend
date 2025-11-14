package phanes.replay.theme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import phanes.replay.theme.domain.ThemeVisit;
import phanes.replay.theme.repository.ThemeVisitRepository;

@Service
@RequiredArgsConstructor
public class ThemeVisitQueryService {

    private final ThemeVisitRepository themeVisitRepository;

    public void save(ThemeVisit themeVisit) {
        themeVisitRepository.save(themeVisit);
    }

    public void delete(ThemeVisit themeVisit) {
        themeVisitRepository.delete(themeVisit);
    }

    public ThemeVisit findByUserIdAndThemeId(Long userId, Long themeId) {
        return themeVisitRepository.findByUserIdAndThemeId(userId, themeId).orElseThrow();
    }
}