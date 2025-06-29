package phanes.replay.gathering.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import phanes.replay.exception.HostNotFoundException;
import phanes.replay.gathering.domain.GatheringMember;
import phanes.replay.gathering.domain.enums.Role;
import phanes.replay.gathering.persistence.repository.GatheringMemberRepository;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GatheringMemberQueryService {

    private final GatheringMemberRepository gatheringMemberRepository;

    public List<GatheringMember> findAllByGatheringIdIn(Set<Long> gatheringIdList) {
        return gatheringMemberRepository.findAllByGatheringIdIn(gatheringIdList);
    }

    public Long countByUserId(Long userId) {
        return gatheringMemberRepository.countByUserId(userId);
    }

    public Long countByUserIdAndRoleEquals(Long userId, Role role) {
        return gatheringMemberRepository.countByUserIdAndRoleEquals(userId, role);
    }

    public Long countByUserIdAndRoleEqualsAndGatheringIdNot(Long userId, Long gatheringId, Role role) {
        return gatheringMemberRepository.countByUserIdAndGatheringIdNotAndRoleEquals(userId, gatheringId, role);
    }

    public List<GatheringMember> findAllByGatheringIdWithUserAndGathering(Long gatheringId) {
        return gatheringMemberRepository.findAllByGatheringId(gatheringId);
    }

    public GatheringMember findHostByUserIdAndGatheringId(Long userId, Long gatheringId) {
        return gatheringMemberRepository.findByUserIdAndGatheringIdAndRoleEquals(userId, gatheringId, Role.HOST)
                .orElseThrow(() -> new HostNotFoundException("Host not found", Map.of("userId", userId, "gatheringId", gatheringId)));
    }

    public GatheringMember findByUserIdAndGatheringId(Long userId, Long gatheringId) {
        return gatheringMemberRepository.findByUserIdAndGatheringId(userId, gatheringId);
    }

    public void save(GatheringMember gatheringMember) {
        gatheringMemberRepository.save(gatheringMember);
    }

    public void delete(GatheringMember member) {
        gatheringMemberRepository.delete(member);
    }

    public void deleteAll(List<GatheringMember> gatheringMemberList) {
        gatheringMemberRepository.deleteAll(gatheringMemberList);
    }
}
