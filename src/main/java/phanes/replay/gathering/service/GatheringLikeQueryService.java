package phanes.replay.gathering.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import phanes.replay.gathering.domain.GatheringLike;
import phanes.replay.gathering.repository.GatheringLikeRepository;

@Service
@RequiredArgsConstructor
public class GatheringLikeQueryService {

    private final GatheringLikeRepository gatheringLikeRepository;

    public void save(GatheringLike gatheringLike) {
        gatheringLikeRepository.save(gatheringLike);
    }

    public void delete(GatheringLike gatheringLike) {
        gatheringLikeRepository.delete(gatheringLike);
    }

    public GatheringLike findByUserIdAndGatheringId(Long userId, Long gatheringId) {
        return gatheringLikeRepository.findByUserIdAndGatheringId(userId, gatheringId).orElseThrow();
    }
}