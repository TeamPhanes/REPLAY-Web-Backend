package phanes.replay.gathering.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phanes.replay.gathering.domain.GatheringContent;

import java.util.Optional;

public interface GatheringContentRepository extends JpaRepository<GatheringContent, Integer> {

    Optional<GatheringContent> findByGatheringId(Long gatheringId);
}