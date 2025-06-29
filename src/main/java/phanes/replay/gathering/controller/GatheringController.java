package phanes.replay.gathering.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import phanes.replay.common.dto.response.Page;
import phanes.replay.exception.DateTimeNotValidException;
import phanes.replay.gathering.dto.request.GatheringCreateRq;
import phanes.replay.gathering.dto.request.GatheringUpdateRq;
import phanes.replay.gathering.dto.response.GatheringDetailRs;
import phanes.replay.gathering.dto.response.GatheringRs;
import phanes.replay.gathering.service.GatheringService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gathering")
public class GatheringController {

    private final GatheringService gatheringService;

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    public Page<List<GatheringRs>> getGatheringList(@AuthenticationPrincipal Long userId, @RequestParam(defaultValue = "dateTime") String sortBy, @RequestParam(required = false) String keyword, @RequestParam(required = false) String city, @RequestParam(required = false) String state, @RequestParam(required = false) LocalDateTime startDate, @RequestParam(required = false) LocalDateTime endDate, @RequestParam(required = false) String genre, @RequestParam Integer limit, @RequestParam Integer offset) {
        userId = userId == null ? 0L : userId;
        startDate = startDate == null ? LocalDateTime.now() : startDate;
        return gatheringService.findAllByKeywordAndCityAndStateAndDateAndGenre(userId, sortBy, keyword, city, state, startDate, endDate, genre, limit, offset);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/host/{hostName}")
    public Page<List<GatheringRs>> getGatheringHostList(@AuthenticationPrincipal Long userId, @PathVariable String hostName, @RequestParam Long gatheringId) {
        if (!StringUtils.hasText(hostName)) {
            return null;
        }
        return gatheringService.findAllByHostNameAndGatheringId(userId, gatheringId, hostName);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/date")
    public Page<List<GatheringRs>> getGatheringDateTimeList(@AuthenticationPrincipal Long userId, @RequestParam LocalDateTime dateTime, @RequestParam Long gatheringId) {
        if (dateTime == null) {
            return null;
        }
        LocalDate targetDate = dateTime.toLocalDate();
        LocalDateTime startDate = targetDate.atStartOfDay();
        LocalDateTime endDate = targetDate.plusDays(1).atStartOfDay();
        return gatheringService.findAllByDateAndGatheringId(userId, gatheringId, startDate, endDate);
    }

    @GetMapping("/{gatheringId}")
    public GatheringDetailRs gatheringDetail(@PathVariable Long gatheringId) {
        return gatheringService.findDetailByGatheringId(gatheringId);
    }

    @PostMapping
    public void createGathering(@AuthenticationPrincipal Long userId, @RequestBody @Valid GatheringCreateRq gatheringCreateRq) {
        if (gatheringCreateRq.getRegistrationStart().isAfter(gatheringCreateRq.getRegistrationEnd())) {
            throw new DateTimeNotValidException("StartDate cannot be after EndDate", gatheringCreateRq.getRegistrationStart(), gatheringCreateRq.getRegistrationEnd());
        }
        gatheringService.createGathering(userId, gatheringCreateRq);
    }

    @PatchMapping("/{gatheringId}")
    public void updateGathering(@AuthenticationPrincipal Long userId, @PathVariable Long gatheringId, @RequestBody @Valid GatheringUpdateRq gatheringUpdateRq) {
        if (gatheringUpdateRq.getRegistrationStart().isAfter(gatheringUpdateRq.getRegistrationEnd())) {
            throw new DateTimeNotValidException("StartDate cannot be after EndDate", gatheringUpdateRq.getRegistrationStart(), gatheringUpdateRq.getRegistrationEnd());
        }
        gatheringService.updateGathering(userId, gatheringId, gatheringUpdateRq);
    }

    @DeleteMapping("/{gatheringId}")
    public void deleteGathering(@AuthenticationPrincipal Long userId, @PathVariable Long gatheringId) {
        gatheringService.deleteGathering(userId, gatheringId);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/{gatheringId}/like")
    public void likeGathering(@AuthenticationPrincipal Long userId, @PathVariable Long gatheringId) {
        gatheringService.likeGathering(userId, gatheringId);
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{gatheringId}/like")
    public void unlikeGathering(@AuthenticationPrincipal Long userId, @PathVariable Long gatheringId) {
        gatheringService.unlikeGathering(userId, gatheringId);
    }
}