package phanes.replay.gathering.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import phanes.replay.gathering.domain.Gathering_Content;

@Repository
public interface Gathering_ContentRepository extends JpaRepository<Gathering_Content, Long> {
}
