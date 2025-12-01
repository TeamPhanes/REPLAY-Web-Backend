package phanes.replay.cafe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import phanes.replay.cafe.domain.Cafe;

public interface CafeRepository extends JpaRepository<Cafe, Long> {

    @Query("SELECT MAX(c.id) FROM Cafe c")
    Long findMaxId();
}