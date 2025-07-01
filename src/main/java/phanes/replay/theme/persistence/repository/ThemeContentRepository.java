package phanes.replay.theme.persistence.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import phanes.replay.theme.domain.ThemeContent;

import java.util.Optional;

public interface ThemeContentRepository extends JpaRepository<ThemeContent, Long> {

    @EntityGraph(attributePaths = "theme")
    Optional<ThemeContent> findByThemeId(Long themeId);
}