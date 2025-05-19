package phanes.replay.gathering.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import phanes.replay.gathering.domain.GatheringContent;
import phanes.replay.gathering.persistence.repository.GatheringContentRepository;

@Service
@RequiredArgsConstructor
public class GatheringContentQueryService {

    private final GatheringContentRepository gatheringContentRepository;

    public void save(GatheringContent gatheringContent) {
        gatheringContentRepository.save(gatheringContent);
    }
}
