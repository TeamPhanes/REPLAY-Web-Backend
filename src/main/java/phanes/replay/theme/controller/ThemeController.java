package phanes.replay.theme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import phanes.replay.theme.dto.response.ThemeRs;
import phanes.replay.theme.service.ThemeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/theme")
public class ThemeController {

    private final ThemeService themeService;

    @GetMapping
    public List<ThemeRs> themeList(@AuthenticationPrincipal Long userId, @RequestParam String sortBy, @RequestParam String keyword, @RequestParam String city, @RequestParam String state, @RequestParam String genre, @RequestParam Integer limit, @RequestParam Integer offset) {
        userId = userId == null ? 0 : userId;
        return themeService.getThemeList(userId, sortBy, keyword, city, state, genre, limit, offset);
    }
}