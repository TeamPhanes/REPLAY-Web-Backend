package phanes.replay.gathering.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phanes.replay.gathering.domain.GatheringScheduleView;

import java.util.List;

public interface GatheringScheduleViewRepository extends JpaRepository<GatheringScheduleView, Long> {
    List<GatheringScheduleView> findAllByUserId(Long userId);
}
