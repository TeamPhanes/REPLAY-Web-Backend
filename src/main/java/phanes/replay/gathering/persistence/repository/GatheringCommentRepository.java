package phanes.replay.gathering.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import phanes.replay.gathering.domain.GatheringComment;

import java.util.List;
import java.util.Optional;

public interface GatheringCommentRepository extends JpaRepository<GatheringComment, Long> {

    @EntityGraph(attributePaths = {"user", "gathering"})
    Page<GatheringComment> findByUserId(@Param("userId") Long userId, Pageable pageable);

    @EntityGraph(attributePaths = {"user", "gathering"})
    Page<GatheringComment> findAllByGatheringId(Long gatheringId, Pageable pageable);

    List<GatheringComment> findAllByGatheringId(Long gatheringId);

    Optional<GatheringComment> findByIdAndGatheringIdAndUserId(Long commentId, Long gatheringId, Long userId);

    Long countByUserId(Long userId);
}
