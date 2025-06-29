package phanes.replay.gathering.persistence.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import phanes.replay.gathering.domain.GatheringContent;

import java.util.Optional;

@Repository
public interface GatheringContentRepository extends JpaRepository<GatheringContent, Long> {

    @EntityGraph(attributePaths = "gathering")
    Optional<GatheringContent> findByGatheringId(Long gatheringId);
}