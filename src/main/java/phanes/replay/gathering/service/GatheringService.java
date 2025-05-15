package phanes.replay.gathering.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import phanes.replay.gathering.controller.GatheringCreateRequest;
import phanes.replay.gathering.controller.GatheringListRequest;
import phanes.replay.gathering.controller.GatheringListResponse;
import phanes.replay.gathering.domain.GatheringScheduleView;
import phanes.replay.gathering.domain.LikeGatheringView;

import java.util.List;

public interface GatheringService {
    void createGathering(GatheringCreateRequest request, Long userId);
    List<GatheringListResponse> getGatheringList(GatheringListRequest request);
    Page<LikeGatheringView> getUserLikeGathering(Long gatheringId, Pageable pageable);

    List<GatheringScheduleView> getUserSchedule(Long userId);
}
