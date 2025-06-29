package phanes.replay.gathering.persistence.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import phanes.replay.gathering.domain.GatheringLike;

import java.util.List;
import java.util.Optional;

public interface GatheringLikeRepository extends JpaRepository<GatheringLike, Long> {

    Long countByUserId(Long userId);

    @EntityGraph(attributePaths = {"user", "gathering"})
    Optional<GatheringLike> findByUserIdAndGatheringId(Long userId, Long gatheringId);

    List<GatheringLike> findAllByGatheringId(Long gatheringId);
}