package phanes.replay.theme.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phanes.replay.theme.domain.ThemeContent;

public interface ThemeContentRepository extends JpaRepository<ThemeContent, Long> {
}
