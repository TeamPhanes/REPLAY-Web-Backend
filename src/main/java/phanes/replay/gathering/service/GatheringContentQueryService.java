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
        return gatheringContentRepository.findByGatheringId(gatheringId)
                .orElseThrow(() -> new GatheringNotFoundException("Gathering Content not found", gatheringId));
    }

    public GatheringContent findByGatheringIdWithGathering(Long gatheringId) {
        return gatheringContentRepository.findByGatheringId(gatheringId)
                .orElseThrow(() -> new GatheringContentNotFoundException("Gathering Content not found", gatheringId));
    }

    public void save(GatheringContent gatheringContent) {
        gatheringContentRepository.save(gatheringContent);
    }

    public void delete(GatheringContent gatheringContent) {
        gatheringContentRepository.delete(gatheringContent);
    }
}