package phanes.replay.theme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import phanes.replay.theme.domain.ParticipatingThemeView;
import phanes.replay.theme.repository.ParticipatingThemeRepository;
import phanes.replay.theme.repository.ParticipatingThemeViewRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipatingThemeService {

    private final ParticipatingThemeRepository participatingThemeRepository;
    private final ParticipatingThemeViewRepository participatingThemeViewRepository;

    public Long getTotalThemeCount(Long userId) {
        return participatingThemeRepository.countByUserId(userId);
    }

    public List<ParticipatingThemeView> getUserPlayingThemeList(Long userId) {
        return participatingThemeViewRepository.findByUserId(userId);
    }
}
