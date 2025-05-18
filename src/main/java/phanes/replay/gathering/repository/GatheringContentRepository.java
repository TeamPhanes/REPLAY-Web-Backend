package phanes.replay.gathering.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import phanes.replay.gathering.domain.GatheringContent;

@Repository
public interface GatheringContentRepository extends JpaRepository<GatheringContent, Long> {
}
