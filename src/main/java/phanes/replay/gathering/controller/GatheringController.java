package phanes.replay.gathering.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import phanes.replay.gathering.dto.response.GatheringDetailRs;
import phanes.replay.gathering.dto.response.GatheringRs;
import phanes.replay.gathering.service.GatheringService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gathering")
public class GatheringController {

    private final GatheringService gatheringService;

    @GetMapping()
    public Page<GatheringRs> getGatheringList(@AuthenticationPrincipal Long userId, @PageableDefault(size = 12) Pageable pageable, @RequestParam(required = false) List<String> locations, @RequestParam(required = false) List<String> genres, @RequestParam(required = false) Long themeId) {
        userId = userId == null ? 0L : userId;
        return gatheringService.findAll(userId, themeId, pageable, locations, genres);
    }

    @GetMapping("/{gatheringId}")
    public GatheringDetailRs getGatheringDetail(@AuthenticationPrincipal Long userId, @PathVariable Long gatheringId) {
        return gatheringService.findById(userId, gatheringId);
    }

    @GetMapping("/date")
    public Page<GatheringRs> getGatheringBetweenDate(@AuthenticationPrincipal Long userId, @PageableDefault(size = 4) Pageable pageable, @RequestParam LocalDateTime date) {
        return gatheringService.findByDateBetween(userId, pageable, date);
    }

    @PostMapping("/like/{gatheringId}")
    public void likeGathering(@AuthenticationPrincipal Long userId, @PathVariable Long gatheringId) {
        gatheringService.saveGatheringLike(userId, gatheringId);
    }

    @DeleteMapping("/like/{gatheringId}")
    public void unLikeGathering(@AuthenticationPrincipal Long userId, @PathVariable Long gatheringId) {
        gatheringService.deleteGatheringLike(userId, gatheringId);
    }
}