package phanes.replay.gathering.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import phanes.replay.gathering.persistence.repository.GatheringLikeRepository;

@Service
@RequiredArgsConstructor
public class GatheringLikeQueryService {

    private final GatheringLikeRepository gatheringLikeRepository;

    public Long countMyLikeGathering(Long userId) {
        return gatheringLikeRepository.countByUserId(userId);
    }
}
