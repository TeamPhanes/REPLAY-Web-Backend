package phanes.replay.gathering.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import phanes.replay.gathering.domain.Gathering;
import phanes.replay.gathering.domain.GatheringMember;
import phanes.replay.gathering.domain.enums.Role;
import phanes.replay.gathering.dto.mapper.GatheringMemberMapper;
import phanes.replay.gathering.dto.response.GatheringMemberRs;
import phanes.replay.gathering.persistence.repository.GatheringMemberRepository;
import phanes.replay.user.domain.User;
import phanes.replay.user.service.UserQueryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GatheringMemberService {

    private final GatheringMemberRepository gatheringMemberRepository;
    private final GatheringMemberMapper gatheringMemberMapper;
    private final UserQueryService userQueryService;
    private final GatheringQueryService gatheringQueryService;

    public List<GatheringMemberRs> getMemberList(Long gatheringId) {
        return gatheringMemberRepository.findAllByGatheringIdWithUser(gatheringId).stream().map(gatheringMemberMapper::toGatheringRs).toList();
    }

    public void addMember(Long userId, Long gatheringId) {
        User user = userQueryService.findByUserId(userId);
        Gathering gathering = gatheringQueryService.findById(gatheringId);
        GatheringMember member = GatheringMember.builder()
                .user(user)
                .gathering(gathering)
                .role(Role.MEMBER)
                .build();
        gatheringMemberRepository.save(member);
    }

    public void deleteMember(Long userId, Long gatheringId) {
        GatheringMember member = gatheringMemberRepository.findByUserIdAndGatheringId(userId, gatheringId);
        gatheringMemberRepository.delete(member);
    }
}
