package phanes.replay.gathering.persistence.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import phanes.replay.gathering.domain.GatheringComment;

import java.util.List;
import java.util.Optional;

public interface GatheringCommentRepository extends JpaRepository<GatheringComment, Long> {

    @Query("SELECT gc FROM GatheringComment gc JOIN gc.gathering WHERE gc.user.id = :userId")
    List<GatheringComment> findByUserId(@Param("userId") Long userId, Pageable pageable);
    @Query("SELECT gc FROM GatheringComment gc JOIN gc.user WHERE gc.gathering.id = :gatheringId ORDER BY gc.createdAt ASC")
    List<GatheringComment> findByGatheringId(Long gatheringId, Pageable pageable);
    Optional<GatheringComment> findByIdAndGatheringIdAndUserId(Long commentId, Long gatheringId, Long userId);
    Long countByUserId(Long userId);
    List<GatheringComment> findAllByGatheringId(Long gatheringId);
}
