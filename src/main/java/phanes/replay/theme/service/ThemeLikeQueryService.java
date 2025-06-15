package phanes.replay.theme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import phanes.replay.exception.LikeNotFoundException;
import phanes.replay.theme.domain.ThemeLike;
import phanes.replay.theme.persistence.repository.ThemeLikeRepository;

@Service
@RequiredArgsConstructor
public class ThemeLikeQueryService {

    private final ThemeLikeRepository themeLikeRepository;

    public Long countByUserId(Long userId) {
        return themeLikeRepository.countByUserId(userId);
    }

    public void save(ThemeLike themeLike) {
        themeLikeRepository.save(themeLike);
    }

    public ThemeLike findByUserIdAndThemeId(Long userId, Long themeId) {
        return themeLikeRepository.findByUserIdAndThemeId(userId, themeId).orElseThrow(() -> new LikeNotFoundException(String.format("Theme Like Not Found - user: %d, theme: %d", userId, themeId)));
    }

    public void delete(ThemeLike themeLike) {
        themeLikeRepository.delete(themeLike);
    }
}
