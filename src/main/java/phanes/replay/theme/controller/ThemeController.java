package phanes.replay.theme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import phanes.replay.theme.dto.ThemeListResponse;
import phanes.replay.theme.dto.ThemeSearchRequest;
import phanes.replay.theme.service.ThemeServiceImpl;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/room")
public class ThemeController {
    private final ThemeServiceImpl themeService;

    @GetMapping
    public ResponseEntity<?> getRooms(@ModelAttribute ThemeSearchRequest request,
                                      @RequestParam("userId") Long userId){

        try {
            List<ThemeListResponse> result = themeService.getThemes(request, userId);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "잘못된 요청"));
        }
    }
}
