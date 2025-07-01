package phanes.replay.theme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import phanes.replay.exception.ThemeNotFoundException;
import phanes.replay.theme.domain.Theme;
import phanes.replay.theme.persistence.repository.ThemeRepository;

@Service
@RequiredArgsConstructor
public class ThemeQueryService {

    private final ThemeRepository themeRepository;

    public Theme findById(Long themeId) {
        return themeRepository.findById(themeId)
                .orElseThrow(() -> new ThemeNotFoundException("Theme not found", themeId));
    }
}