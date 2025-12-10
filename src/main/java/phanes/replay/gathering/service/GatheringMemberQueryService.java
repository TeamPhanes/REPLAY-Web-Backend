package phanes.replay.gathering.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import phanes.replay.gathering.domain.GatheringMember;
import phanes.replay.gathering.repository.GatheringMemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GatheringMemberQueryService {

    private final GatheringMemberRepository gatheringMemberRepository;

    public void save(GatheringMember gatheringMember) {
        gatheringMemberRepository.save(gatheringMember);
    }

    public GatheringMember findByUserId(Long userId, Long gatheringId) {
        return gatheringMemberRepository.findByUserIdAndGatheringId(userId, gatheringId).orElseThrow();
    }

    public List<GatheringMember> findAllByGatheringId(Long gatheringId) {
        return gatheringMemberRepository.findAllByGatheringId(gatheringId);
    }

    public void deleteAll(List<GatheringMember> gatheringMemberList) {
        gatheringMemberRepository.deleteAll(gatheringMemberList);
    }
}