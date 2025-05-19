package phanes.replay.gathering.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import phanes.replay.exception.GatheringNotFoundException;
import phanes.replay.gathering.domain.Gathering;
import phanes.replay.gathering.persistence.repository.GatheringRepository;

@Service
@RequiredArgsConstructor
public class GatheringQueryService {

    private final GatheringRepository gatheringRepository;

    public Gathering findById(Long id) {
        return gatheringRepository.findById(id).orElseThrow(() -> new GatheringNotFoundException("Gathering Not Found"));
    }

    public void save(Gathering gatheringContent) {
        gatheringRepository.save(gatheringContent);
    }
}
