package phanes.replay.gathering.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phanes.replay.gathering.domain.Gathering;

public interface GatheringRepository extends JpaRepository<Gathering, Long> {

    Long countByThemeId(Long themeId);
}