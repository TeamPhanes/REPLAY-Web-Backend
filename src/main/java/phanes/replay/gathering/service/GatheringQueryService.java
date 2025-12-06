package phanes.replay.gathering.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import phanes.replay.gathering.domain.Gathering;
import phanes.replay.gathering.repository.GatheringRepository;

@Service
@RequiredArgsConstructor
public class GatheringQueryService {

    private final GatheringRepository gatheringRepository;

    public Gathering findById(Long gatheringId) {
        return  gatheringRepository.findById(gatheringId).orElseThrow();
    }

    public void save(Gathering gathering) {
        gatheringRepository.save(gathering);
    }
}