package phanes.replay.theme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import phanes.replay.theme.persistence.repository.ThemeLikeRepository;

@Service
@RequiredArgsConstructor
public class ThemeLikeQueryService {

    private final ThemeLikeRepository themeLikeRepository;

    public Long countMyLikeTheme(Long userId) {
        return themeLikeRepository.countByUserId(userId);
    }
}
