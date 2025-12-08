package phanes.replay.theme.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import phanes.replay.common.dto.response.Cursor;
import phanes.replay.common.dto.response.SearchPage;
import phanes.replay.theme.dto.response.ThemeDetailRs;
import phanes.replay.theme.dto.response.ThemePreviewRs;
import phanes.replay.theme.dto.response.ThemeRs;
import phanes.replay.theme.dto.response.ThemeSearchRs;
import phanes.replay.theme.service.ThemeService;
import phanes.replay.utils.CursorUtils;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/theme")
public class ThemeController {

    private final ThemeService themeService;

    @GetMapping("/preview")
    public Page<ThemePreviewRs> getPreviewThemeOrderByCreatedAt(@PageableDefault Pageable pageable, @RequestParam(required = false) String genre) {
        return themeService.findAllPreview(pageable, genre);
    }

    @GetMapping()
    public Page<ThemeRs> getThemeList(@AuthenticationPrincipal Long userId, @PageableDefault(size = 12) Pageable pageable, @RequestParam(required = false) List<String> locations, @RequestParam(required = false) List<String> genres) {
        userId = userId == null ? 0L : userId;
        return themeService.findAll(userId, pageable, locations, genres);
    }

    @GetMapping("/search")
    public SearchPage<ThemeSearchRs> getThemeSearchList(@RequestParam Integer size, @RequestParam String keyword, @RequestParam(required = false) String cursor) {
        Cursor decoded = null;
        if (cursor != null) {
            decoded = CursorUtils.decode(cursor);
        }
        return themeService.findAllSearchByKeyword(size, keyword, decoded);
    }

    @GetMapping("/{themeId}")
    public ThemeDetailRs getThemeDetail(@AuthenticationPrincipal Long userId, @PathVariable Long themeId) {
        userId = userId == null ? 0L : userId;
        return themeService.findById(userId, themeId);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/like")
    public Page<ThemeRs> getLikeTheme(@AuthenticationPrincipal Long userId, @PageableDefault(size = 12) Pageable pageable, @RequestParam(required = false) List<String> locations, @RequestParam(required = false) List<String> genres) {
        return themeService.findAllByLike(userId, pageable, locations, genres);
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
}