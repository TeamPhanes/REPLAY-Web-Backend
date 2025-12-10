package phanes.replay.gathering.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phanes.replay.gathering.domain.GatheringLike;

import java.util.List;
import java.util.Optional;

public interface GatheringLikeRepository extends JpaRepository<GatheringLike,Long> {

    Optional<GatheringLike> findByUserIdAndGatheringId(Long userId, Long gatheringId);

    List<GatheringLike> findAllByGatheringId(Long gatheringId);
}