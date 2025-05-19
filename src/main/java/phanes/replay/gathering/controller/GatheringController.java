package phanes.replay.gathering.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import phanes.replay.gathering.dto.request.GatheringCreateRq;
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
    public List<GatheringRs> gatheringList(@AuthenticationPrincipal Long userId, @RequestParam String sortBy, @RequestParam String keyword, @RequestParam String city, @RequestParam String state, @RequestParam LocalDateTime date, @RequestParam String genre, @RequestParam Integer limit, @RequestParam Integer offset) {
        userId = userId == null ? 0L : userId;
        return gatheringService.getGatheringList(userId, sortBy, keyword, city, state, date, genre, limit, offset);
    }

    @GetMapping("/{gatheringId}")
    public GatheringDetailRs gatheringDetail(@PathVariable Long gatheringId) {
        return gatheringService.getGatheringDetail(gatheringId);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void createGathering(@AuthenticationPrincipal Long userId, @ModelAttribute @Valid GatheringCreateRq gatheringCreateRq) {
        gatheringService.createGathering(userId, gatheringCreateRq);
    }
}