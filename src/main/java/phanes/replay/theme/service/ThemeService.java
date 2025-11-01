package phanes.replay.theme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RestController;
import phanes.replay.theme.dto.response.ThemePreviewRs;
import phanes.replay.theme.mapper.ThemeMapper;
import phanes.replay.theme.repository.ThemeJooqRepository;
import phanes.replay.theme.repository.ThemeRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ThemeService {

    private final ThemeJooqRepository themeJooqRepository;
    private final ThemeRepository themeRepository;
    private final ThemeMapper themeMapper;

    public List<ThemePreviewRs> findAllByThemePreviewOrderByLike(int size) {
        return themeJooqRepository.findAllOrderByThemeLike(size)
                .stream()
                .map(themeMapper::toThemePreview)
                .toList();
    }

    public List<ThemePreviewRs> findAllByThemePreviewOrderByCreatedAt(int size) {
        Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Order.desc("createdAt")));
        return themeRepository.findAllBy(pageable)
                .stream()
                .map(themeMapper::toThemePreview)
                .toList();
    }
}