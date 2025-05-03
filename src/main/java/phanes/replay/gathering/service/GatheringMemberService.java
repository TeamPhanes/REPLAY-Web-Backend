package phanes.replay.gathering.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import phanes.replay.gathering.domain.Gathering_Member;
import phanes.replay.gathering.domain.ParticipatingGatheringView;
import phanes.replay.gathering.domain.Role;
import phanes.replay.gathering.repository.Gathering_MemberRepository;
import phanes.replay.gathering.repository.ParticipatingGatheringViewRepository;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GatheringMemberService {

    private final Gathering_MemberRepository gatheringMemberRepository;
    private final ParticipatingGatheringViewRepository participatingGatheringViewRepository;

    public Long getTotalGatheringCount(Long userId) {
        return gatheringMemberRepository.countByUserIdAndRoleEquals(userId, Role.HOST);
    }

    public Long getTotalMakeGatheringCount(Long userId, Role role) {
        return gatheringMemberRepository.countByUserIdAndRoleEquals(userId, role);
    }

    public List<ParticipatingGatheringView> getParticipatingGatheringView(Long userId, Pageable pageable) {
        return participatingGatheringViewRepository.findAllByUserId(userId, pageable);
    }

    public List<Gathering_Member> getMemberList(Set<Long> gatheringIdList) {
        return gatheringMemberRepository.findAllByMember(gatheringIdList);
    }
}
