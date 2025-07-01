package phanes.replay.theme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import phanes.replay.exception.ThemeNotFoundException;
import phanes.replay.theme.domain.ThemeContent;
import phanes.replay.theme.persistence.repository.ThemeContentRepository;

@Service
@RequiredArgsConstructor
public class ThemeContentQueryService {

    private final ThemeContentRepository themeContentRepository;

    public ThemeContent findByThemeId(Long themeId) {
        return themeContentRepository.findByThemeId(themeId)
                .orElseThrow(() -> new ThemeNotFoundException("Theme Content not found", themeId));
    }
}