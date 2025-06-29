package phanes.replay.gathering.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import phanes.replay.gathering.domain.Gathering;
import phanes.replay.gathering.domain.GatheringMember;
import phanes.replay.gathering.domain.enums.Role;
import phanes.replay.gathering.dto.mapper.GatheringMemberMapper;
import phanes.replay.gathering.dto.response.GatheringMemberRs;
import phanes.replay.user.domain.User;
import phanes.replay.user.service.UserQueryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GatheringMemberService {

    private final GatheringMemberQueryService gatheringMemberQueryService;
    private final GatheringMemberMapper gatheringMemberMapper;
    private final GatheringQueryService gatheringQueryService;
    private final UserQueryService userQueryService;

    public List<GatheringMemberRs> findAllByGatheringId(Long gatheringId) {
        return gatheringMemberQueryService.findAllByGatheringIdWithUserAndGathering(gatheringId)
                .stream()
                .map(gatheringMemberMapper::toGatheringRs)
                .toList();
    }

    public void saveMember(Long userId, Long gatheringId) {
        User user = userQueryService.findById(userId);
        Gathering gathering = gatheringQueryService.findById(gatheringId);
        GatheringMember member = GatheringMember.builder()
                .user(user)
                .gathering(gathering)
                .role(Role.MEMBER)
                .build();
        gatheringMemberQueryService.save(member);
    }

    public void deleteMember(Long userId, Long gatheringId) {
        GatheringMember member = gatheringMemberQueryService.findByUserIdAndGatheringId(userId, gatheringId);
        gatheringMemberQueryService.delete(member);
    }
}