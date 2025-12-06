package phanes.replay.gathering.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phanes.replay.gathering.domain.GatheringContent;

public interface GatheringContentRepository extends JpaRepository<GatheringContent, Integer> {
}