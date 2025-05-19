package phanes.replay.gathering.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import phanes.replay.gathering.dto.response.GatheringRs;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GatheringService {


    public List<GatheringRs> getGatheringList(Long userId, String sortBy, String keyword, String city, String state, LocalDateTime date, String genre, Integer limit, Integer offset) {

        return null;
    }
}
