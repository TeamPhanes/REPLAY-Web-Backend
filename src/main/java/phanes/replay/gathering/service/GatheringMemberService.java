package phanes.replay.gathering.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import phanes.replay.gathering.domain.Role;
import phanes.replay.gathering.repository.Gathering_MemberRepository;

@Service
@RequiredArgsConstructor
public class GatheringMemberService {

    private final Gathering_MemberRepository gatheringMemberRepository;

    public Long getTotalGatheringCount(Long userId) {
        return gatheringMemberRepository.countByUserIdAndRoleEquals(userId, Role.HOST);
    }

    public Long getTotalMakeGatheringCount(Long userId, Role role) {
        return gatheringMemberRepository.countByUserIdAndRoleEquals(userId, role);
    }
}
