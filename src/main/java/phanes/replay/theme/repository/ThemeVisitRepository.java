package phanes.replay.theme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phanes.replay.theme.domain.ThemeVisit;

import java.util.Optional;

public interface ThemeVisitRepository extends JpaRepository<ThemeVisit, Long> {

    Optional<ThemeVisit> findByUserIdAndThemeId(Long userId, Long themeId);
}