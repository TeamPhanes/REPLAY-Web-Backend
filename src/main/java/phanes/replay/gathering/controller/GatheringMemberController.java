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
    public List<GatheringMemberRs> memberList(@PathVariable Long gatheringId) {
        return gatheringMemberService.getMemberList(gatheringId);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public void addMember(@AuthenticationPrincipal Long userId, @RequestParam Long gatheringId) {
        gatheringMemberService.addMember(userId, gatheringId);
    }
}
