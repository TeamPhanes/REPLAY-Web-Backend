package phanes.replay.gathering.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import phanes.replay.gathering.domain.GatheringComment;

public interface GatheringCommentRepository extends JpaRepository<GatheringComment, Long> {

    @Query("SELECT gc FROM GatheringComment gc JOIN gc.gathering WHERE gc.user.id = :userId")
    Page<GatheringComment> findByUserId(@Param("userId") Long userId, Pageable pageable);
    @Query("SELECT gc FROM GatheringComment gc JOIN gc.user WHERE gc.gathering.id = :gatheringId")
    Page<GatheringComment> findByGatheringId(Long gatheringId, Pageable pageable);
}
