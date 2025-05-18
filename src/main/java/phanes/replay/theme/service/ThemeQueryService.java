package phanes.replay.theme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import phanes.replay.exception.ThemeNotFoundException;
import phanes.replay.theme.domain.Theme;
import phanes.replay.theme.repository.ThemeRepository;

@Service
@RequiredArgsConstructor
public class ThemeQueryService {

    private final ThemeRepository themeRepository;

    public Theme getTheme(Long themeId) {
        return themeRepository.findById(themeId).orElseThrow(() -> new ThemeNotFoundException("theme not found"));
    }
}
