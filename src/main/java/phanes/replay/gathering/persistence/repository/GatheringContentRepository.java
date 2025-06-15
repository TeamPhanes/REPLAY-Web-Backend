package phanes.replay.gathering.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import phanes.replay.gathering.domain.GatheringContent;

import java.util.Optional;

@Repository
public interface GatheringContentRepository extends JpaRepository<GatheringContent, Long> {

    @Query("SELECT GC FROM GatheringContent GC JOIN FETCH GC.gathering WHERE GC.gathering.id = :gatheringId")
    Optional<GatheringContent> findByGatheringIdWithGathering(Long gatheringId);
}
