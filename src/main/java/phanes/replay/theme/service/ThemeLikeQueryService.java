package phanes.replay.theme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import phanes.replay.theme.domain.ThemeLike;
import phanes.replay.theme.persistence.repository.ThemeLikeRepository;

@Service
@RequiredArgsConstructor
public class ThemeLikeQueryService {

    private final ThemeLikeRepository themeLikeRepository;

    public Long countMyLikeTheme(Long userId) {
        return themeLikeRepository.countByUserId(userId);
    }

    public void save(ThemeLike themeLike) {
        themeLikeRepository.save(themeLike);
    }

    public ThemeLike findByUserIdAndThemeId(Long userId, Long themeId) {
        return themeLikeRepository.findByUserIdAndThemeId(userId, themeId);
    }

    public void delete(ThemeLike themeLike) {
        themeLikeRepository.delete(themeLike);
    }
}
