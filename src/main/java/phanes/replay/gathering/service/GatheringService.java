package phanes.replay.gathering.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import phanes.replay.gathering.domain.GatheringScheduleView;
import phanes.replay.gathering.domain.LikeGatheringView;
import phanes.replay.gathering.dto.request.CreateGatheringRq;
import phanes.replay.gathering.dto.request.GatheringRq;
import phanes.replay.gathering.dto.response.GatheringRs;

import java.util.List;

public interface GatheringService {
    void createGathering(CreateGatheringRq request, Long userId);
    List<GatheringRs> getGatheringList(GatheringRq request);
    Page<LikeGatheringView> getUserLikeGathering(Long gatheringId, Pageable pageable);

    List<GatheringScheduleView> getUserSchedule(Long userId);
}
