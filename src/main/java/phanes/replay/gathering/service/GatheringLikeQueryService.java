package phanes.replay.gathering.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import phanes.replay.exception.LikeNotFoundException;
import phanes.replay.gathering.domain.GatheringLike;
import phanes.replay.gathering.persistence.repository.GatheringLikeRepository;

@Service
@RequiredArgsConstructor
public class GatheringLikeQueryService {

    private final GatheringLikeRepository gatheringLikeRepository;

    public Long countMyLikeGathering(Long userId) {
        return gatheringLikeRepository.countByUserId(userId);
    }

    public void save(GatheringLike gatheringLike) {
        gatheringLikeRepository.save(gatheringLike);
    }

    public GatheringLike findByUserIdAndGatheringId(Long userId, Long gatheringId) {
        return gatheringLikeRepository.findByUserIdAndGatheringId(userId, gatheringId).orElseThrow(() -> new LikeNotFoundException("Gathering Like not found"));
    }

    public void delete(GatheringLike gatheringLike) {
        gatheringLikeRepository.delete(gatheringLike);
    }
}