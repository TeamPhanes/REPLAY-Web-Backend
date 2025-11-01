package phanes.replay.theme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import phanes.replay.theme.dto.response.ThemePreviewRs;
import phanes.replay.theme.service.ThemeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/theme")
public class ThemeController {

    private final ThemeService themeService;

    @GetMapping("/preview/like")
    public List<ThemePreviewRs> getPreviewThemeOrderByLike(@RequestParam int size) {
        return themeService.findAllByThemePreviewOrderByLike(size);
    }

    @GetMapping("/preview/new")
    public List<ThemePreviewRs> getPreviewThemeOrderByCreatedAt(@RequestParam int size) {
        return themeService.findAllByThemePreviewOrderByCreatedAt(size);
    }
}