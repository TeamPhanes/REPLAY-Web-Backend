package phanes.replay.gathering.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import phanes.replay.gathering.dto.mapper.GatheringMapper;
import phanes.replay.gathering.dto.response.GatheringRs;
import phanes.replay.gathering.persistence.mapper.GatheringQueryMapper;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GatheringService {

    private final GatheringQueryMapper gatheringQueryMapper;
    private final GatheringMapper gatheringMapper;

    public List<GatheringRs> getGatheringList(Long userId, String sortBy, String keyword, String city, String state, LocalDateTime date, String genre, Integer limit, Integer offset) {
        return gatheringQueryMapper.findAllByKeywordAndAddress(userId, sortBy, keyword, city, state, date, genre, limit, offset).stream()
                .map(gatheringMapper::toGatheringRs).toList();
    }
}
