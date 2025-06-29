package phanes.replay.gathering.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import phanes.replay.gathering.dto.response.GatheringMemberRs;
import phanes.replay.gathering.service.GatheringMemberService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gathering/member")
public class GatheringMemberController {

    private final GatheringMemberService gatheringMemberService;

    @GetMapping("/{gatheringId}")
    public List<GatheringMemberRs> getMemberList(@PathVariable Long gatheringId) {
        return gatheringMemberService.findAllByGatheringId(gatheringId);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/{gatheringId}")
    public void addMember(@AuthenticationPrincipal Long userId, @PathVariable Long gatheringId) {
        gatheringMemberService.saveMember(userId, gatheringId);
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{gatheringId}")
    public void deleteMember(@AuthenticationPrincipal Long userId, @PathVariable Long gatheringId) {
        gatheringMemberService.deleteMember(userId, gatheringId);
    }
}