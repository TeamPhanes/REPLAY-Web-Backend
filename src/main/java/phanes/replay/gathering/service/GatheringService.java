package phanes.replay.gathering.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import phanes.replay.gathering.dto.GatheringListRequest;
import phanes.replay.gathering.dto.GatheringListResponse;
import phanes.replay.gathering.dto.GatheringCreateRequest;
import phanes.replay.gathering.dto.GatheringResponseDto;

import java.util.List;

public interface GatheringService {
    void createGathering(GatheringCreateRequest request, Long userId);

    List<GatheringListResponse> getGatheringList(GatheringListRequest request);

    public Page<GatheringResponseDto> getGatheringList(Pageable pageable);
}
