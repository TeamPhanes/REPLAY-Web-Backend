package phanes.replay.theme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import phanes.replay.exception.ThemeVisitNotFoundException;
import phanes.replay.theme.domain.ThemeVisit;
import phanes.replay.theme.persistence.repository.ThemeVisitRepository;

@Service
@RequiredArgsConstructor
public class ThemeVisitQueryService {

    private final ThemeVisitRepository themeVisitRepository;

    public ThemeVisit findByUserIdAndThemeId(Long userId, Long themeId) {
        return themeVisitRepository.findByUserIdAndThemeId(userId, themeId)
                .orElseThrow(() -> new ThemeVisitNotFoundException("Visit Theme not found", userId, themeId));
    }

    public Long countByUserId(Long userId) {
        return themeVisitRepository.countByUserId(userId);
    }

    public void save(ThemeVisit themeVisit) {
        themeVisitRepository.save(themeVisit);
    }

    public void delete(ThemeVisit themeVisit) {
        themeVisitRepository.delete(themeVisit);
    }
}