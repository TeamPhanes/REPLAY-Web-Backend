package phanes.replay.gathering.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import phanes.replay.gathering.dto.request.GatheringCreateRq;
import phanes.replay.gathering.dto.request.GatheringUpdateRq;
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

    @GetMapping
    public List<GatheringRs> gatheringList(@AuthenticationPrincipal Long userId, @RequestParam(defaultValue = "dateTime") String sortBy, @RequestParam(required = false) String keyword, @RequestParam(required = false) String city, @RequestParam(required = false) String state, @RequestParam(required = false) LocalDateTime startDate, @RequestParam(required = false) LocalDateTime endDate, @RequestParam(required = false) String genre, @RequestParam Integer limit, @RequestParam Integer offset) {
        userId = userId == null ? 0L : userId;
        startDate = startDate == null ? LocalDateTime.now() : startDate;
        return gatheringService.getGatheringList(userId, sortBy, keyword, city, state, startDate, endDate, genre, limit, offset);
    }

    @GetMapping("/{gatheringId}")
    public GatheringDetailRs gatheringDetail(@PathVariable Long gatheringId) {
        return gatheringService.getGatheringDetail(gatheringId);
    }

    @PostMapping
    public void createGathering(@AuthenticationPrincipal Long userId, @RequestBody @Valid GatheringCreateRq gatheringCreateRq) {
        gatheringService.createGathering(userId, gatheringCreateRq);
    }

    @PatchMapping("/{gatheringId}")
    public void updateGathering(@AuthenticationPrincipal Long userId, @PathVariable Long gatheringId, @RequestBody @Valid GatheringUpdateRq gatheringUpdateRq) {
        gatheringService.updateGathering(userId, gatheringId, gatheringUpdateRq);
    }

    @DeleteMapping("/{gatheringId}")
    public void deleteGathering(@AuthenticationPrincipal Long userId, @PathVariable Long gatheringId) {
        gatheringService.deleteGathering(userId, gatheringId);
    }
}