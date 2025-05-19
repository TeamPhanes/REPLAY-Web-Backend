package phanes.replay.gathering.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
}