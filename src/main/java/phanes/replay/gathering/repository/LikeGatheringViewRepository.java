package phanes.replay.gathering.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import phanes.replay.gathering.domain.LikeGatheringView;

public interface LikeGatheringViewRepository extends JpaRepository<LikeGatheringView, Long> {
    Page<LikeGatheringView> findByUserId(Long userId, Pageable pageable);
}
