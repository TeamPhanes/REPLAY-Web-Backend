package phanes.replay.theme.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import phanes.replay.theme.dto.response.ThemeDetailRs;
import phanes.replay.theme.dto.response.ThemePreviewRs;
import phanes.replay.theme.dto.response.ThemeRs;
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

    @GetMapping()
    public Page<ThemeRs> getThemeList(@AuthenticationPrincipal Long userId, @PageableDefault(size = 12) Pageable pageable, @RequestParam(required = false) List<String> locations, @RequestParam(required = false) List<String> genres) {
        userId = userId == null ? 0L : userId;
        return themeService.findAll(userId, pageable, locations, genres);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/like")
    public Page<ThemeRs> getLikeTheme(@AuthenticationPrincipal Long userId, @PageableDefault(size = 12) Pageable pageable, @RequestParam(required = false) List<String> locations, @RequestParam(required = false) List<String> genres) {
        return themeService.findAllByLike(userId, pageable, locations, genres);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/visit")
    public Page<ThemeRs> getVisitTheme(@AuthenticationPrincipal Long userId, @PageableDefault(size = 12) Pageable pageable, @RequestParam(required = false) List<String> locations, @RequestParam(required = false) List<String> genres) {
        return themeService.findAllByVisit(userId, pageable, locations, genres);
    }

    @PostMapping("/like/{themeId}")
    public void likeTheme(@AuthenticationPrincipal Long userId, @PathVariable Long themeId) {
        themeService.saveThemeLike(userId, themeId);
    }

    @PostMapping("/visit/{themeId}")
    public void visitTheme(@AuthenticationPrincipal Long userId, @PathVariable Long themeId) {
        themeService.saveThemeVisit(userId, themeId);
    }

    @DeleteMapping("/like/{themeId}")
    public void unLikeTheme(@AuthenticationPrincipal Long userId, @PathVariable Long themeId) {
        themeService.deleteThemeLike(userId, themeId);
    }

    @DeleteMapping("/visit/{themeId}")
    public void unVisitTheme(@AuthenticationPrincipal Long userId, @PathVariable Long themeId) {
        themeService.deleteThemeVisit(userId, themeId);
    }

    @GetMapping("/{themeId}")
    public ThemeDetailRs getThemeDetail(@AuthenticationPrincipal Long userId, @PathVariable Long themeId) {
        userId = userId == null ? 0L : userId;
        return themeService.findByThemeId(userId, themeId);
    }
}