package phanes.replay.theme.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import phanes.replay.theme.domain.ThemeVisit;

@Repository
public interface ThemeVisitRepository extends JpaRepository<ThemeVisit, Long> {
    Long countByUserId(Long userId);
}
