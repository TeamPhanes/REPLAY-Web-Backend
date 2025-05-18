package phanes.replay.theme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import phanes.replay.theme.repository.ThemeVisitRepository;

@Service
@RequiredArgsConstructor
public class ThemeVisitQueryService {

    private final ThemeVisitRepository themeVisitRepository;

    public Long countByUserId(Long userId) {
        return themeVisitRepository.countByUserId(userId);
    }
}
