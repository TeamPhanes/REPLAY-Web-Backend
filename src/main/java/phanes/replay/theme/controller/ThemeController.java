package phanes.replay.theme.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import phanes.replay.common.dto.response.Page;
import phanes.replay.theme.dto.response.ThemeDetailRs;
import phanes.replay.theme.dto.response.ThemeRs;
import phanes.replay.theme.dto.response.ThemeSearchRs;
import phanes.replay.theme.service.ThemeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/theme")
public class ThemeController {

    private final ThemeService themeService;

    @GetMapping
    public Page<List<ThemeRs>> getThemeList(@AuthenticationPrincipal Long userId, @RequestParam(defaultValue = "likes") String sortBy, @RequestParam(required = false) String keyword, @RequestParam(required = false) String city, @RequestParam(required = false) String state, @RequestParam(required = false) String genre, @RequestParam Integer limit, @RequestParam Integer offset) {
        userId = userId == null ? 0 : userId;
        return themeService.findAllByKeywordAndCityAndStateAndGenre(userId, sortBy, keyword, city, state, genre, limit, offset);
    }

    @GetMapping("/{themeId}")
    public ThemeDetailRs getThemeDetail(@PathVariable Long themeId) {
        return themeService.findDetailByThemeId(themeId);
    }

    @GetMapping("/search")
    public List<ThemeSearchRs> getThemeSearchList(@RequestParam(required = false) String keyword, @RequestParam(required = false) String city, @RequestParam(required = false) String state) {
        return themeService.findAllByKeywordAndCityAndState(keyword, city, state);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/{themeId}/like")
    public void likeTheme(@AuthenticationPrincipal Long userId, @PathVariable Long themeId) {
        themeService.likeTheme(userId, themeId);
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{themeId}/like")
    public void unlikeTheme(@AuthenticationPrincipal Long userId, @PathVariable Long themeId) {
        themeService.unlikeTheme(userId, themeId);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/{themeId}/visit")
    public void visitTheme(@AuthenticationPrincipal Long userId, @PathVariable Long themeId) {
        themeService.visitTheme(userId, themeId);
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{themeId}/visit")
    public void deleteVisitTheme(@AuthenticationPrincipal Long userId, @PathVariable Long themeId) {
        themeService.deleteVisitTheme(userId, themeId);
    }
}