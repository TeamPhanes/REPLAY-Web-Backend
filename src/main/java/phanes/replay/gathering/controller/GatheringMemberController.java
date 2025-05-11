package phanes.replay.gathering.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
