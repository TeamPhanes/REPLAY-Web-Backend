package phanes.replay.theme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import phanes.replay.theme.repository.ParticipatingThemeRepository;

@Service
@RequiredArgsConstructor
public class ParticipatingThemeService {

    private final ParticipatingThemeRepository participatingThemeRepository;

    public Long getTotalThemeCount(Long userId) {
        return participatingThemeRepository.countByUserId(userId);
    }
}
