package phanes.replay.gathering.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import phanes.replay.gathering.domain.GatheringMember;
import phanes.replay.gathering.repository.GatheringMemberRepository;

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
}