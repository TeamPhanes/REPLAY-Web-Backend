package phanes.replay.gathering.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phanes.replay.gathering.domain.LikeGatheringView;

import java.util.List;

public interface LikeGatheringViewRepository extends JpaRepository<LikeGatheringView, Long> {
    List<LikeGatheringView> findByUserId(Long userId);
}
