package phanes.replay.gathering.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phanes.replay.gathering.domain.GatheringComment;

import java.util.List;

public interface GatheringCommentRepository extends JpaRepository<GatheringComment, Long> {

    List<GatheringComment> findAllByGatheringId(Long gatheringId);
}