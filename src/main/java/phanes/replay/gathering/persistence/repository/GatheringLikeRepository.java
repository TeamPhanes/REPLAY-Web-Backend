package phanes.replay.gathering.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import phanes.replay.gathering.domain.GatheringLike;

import java.util.List;
import java.util.Optional;

public interface GatheringLikeRepository extends JpaRepository<GatheringLike, Long> {

    Long countByUserId(Long userId);
    @Query("SELECT gl FROM GatheringLike gl JOIN FETCH gl.user u JOIN FETCH gl.gathering g WHERE u.id = :userId AND g.id = :gatheringId")
    Optional<GatheringLike> findByUserIdAndGatheringId(Long userId, Long gatheringId);

    List<GatheringLike> findAllByGatheringId(Long gatheringId);
}
