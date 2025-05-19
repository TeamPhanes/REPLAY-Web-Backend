package phanes.replay.gathering.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import phanes.replay.gathering.domain.Gathering;

@Repository
public interface GatheringRepository extends JpaRepository<Gathering, Long> {
}
