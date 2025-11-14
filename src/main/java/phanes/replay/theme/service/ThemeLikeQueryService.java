package phanes.replay.theme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import phanes.replay.theme.domain.ThemeLike;
import phanes.replay.theme.repository.ThemeLikeRepository;

@Service
@RequiredArgsConstructor
public class ThemeLikeQueryService {

    private final ThemeLikeRepository themeLikeRepository;

    public void save(ThemeLike themeLike) {
        themeLikeRepository.save(themeLike);
    }

    public void delete(ThemeLike themeLike) {
        themeLikeRepository.delete(themeLike);
    }

    public ThemeLike findByUserIdAndThemeId(Long userId, Long themeId) {
        return themeLikeRepository.findByUserIdAndThemeId(userId, themeId).orElseThrow();
    }
}