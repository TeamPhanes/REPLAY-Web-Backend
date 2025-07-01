package phanes.replay.theme.persistence.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import phanes.replay.theme.domain.ThemeVisit;

import java.util.Optional;

@Repository
public interface ThemeVisitRepository extends JpaRepository<ThemeVisit, Long> {
    Long countByUserId(Long userId);

    @EntityGraph(attributePaths = {"user", "theme"})
    Optional<ThemeVisit> findByUserIdAndThemeId(Long userId, Long themeId);
}