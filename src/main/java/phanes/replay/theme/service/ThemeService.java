package phanes.replay.theme.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import phanes.replay.theme.domain.ThemeLikeView;
import phanes.replay.theme.repository.ThemeLikeViewRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ThemeService {

    private final ThemeLikeViewRepository themeLikeViewRepository;

    public List<ThemeLikeView> getUserLikeTheme(Long userId, Pageable pageable) {
        return themeLikeViewRepository.findByUserId(userId, pageable);
    }
}
