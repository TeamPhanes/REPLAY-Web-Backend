package phanes.replay.gathering.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import phanes.replay.exception.GatheringContentNotFoundException;
import phanes.replay.exception.GatheringNotFoundException;
import phanes.replay.gathering.domain.GatheringContent;
import phanes.replay.gathering.persistence.repository.GatheringContentRepository;

@Service
@RequiredArgsConstructor
public class GatheringContentQueryService {

    private final GatheringContentRepository gatheringContentRepository;

    public GatheringContent findByGatheringId(Long gatheringId) {
        return gatheringContentRepository.findById(gatheringId).orElseThrow(() -> new GatheringNotFoundException("gathering not found"));
    }

    public GatheringContent findByGatheringIdWithGathering(Long gatheringId) {
        return gatheringContentRepository.findByGatheringIdWithGathering(gatheringId).orElseThrow(() -> new GatheringContentNotFoundException("gathering content not found"));
    }

    public void save(GatheringContent gatheringContent) {
        gatheringContentRepository.save(gatheringContent);
    }

    public void delete(GatheringContent gatheringContent) {
        gatheringContentRepository.delete(gatheringContent);
    }
}
