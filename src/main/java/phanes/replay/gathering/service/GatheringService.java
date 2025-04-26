package phanes.replay.gathering.service;


import phanes.replay.gathering.controller.GatheringListRequest;
import phanes.replay.gathering.controller.GatheringListResponse;
import phanes.replay.gathering.controller.GatheringCreateRequest;

import java.util.List;

public interface GatheringService {
    void createGathering(GatheringCreateRequest request, Long userId);

    List<GatheringListResponse> getGatheringList(GatheringListRequest request);
}
