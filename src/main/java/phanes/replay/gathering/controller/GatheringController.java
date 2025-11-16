package phanes.replay.gathering.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import phanes.replay.gathering.dto.response.GatheringRs;
import phanes.replay.gathering.service.GatheringService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gathering")
public class GatheringController {

    private final GatheringService gatheringService;

    @GetMapping("/{themeId}")
    public Page<GatheringRs> getGatheringByThemeId(@AuthenticationPrincipal Long userId, @PageableDefault(size = 2) Pageable pageable, @PathVariable Long themeId) {
        return gatheringService.findByThemeId(userId, pageable, themeId);
    }

    @PostMapping("/like/{gatheringId}")
    public void likeGathering (@AuthenticationPrincipal Long userId, @PathVariable Long gatheringId) {
        gatheringService.saveGatheringLike(userId, gatheringId);
    }

    @DeleteMapping("/like/{gatheringId}")
    public void unLikeGathering(@AuthenticationPrincipal Long userId, @PathVariable Long gatheringId) {
        gatheringService.deleteGatheringLike(userId, gatheringId);
    }
}