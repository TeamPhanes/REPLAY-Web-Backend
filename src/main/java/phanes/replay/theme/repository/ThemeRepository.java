package phanes.replay.theme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phanes.replay.theme.domain.Theme;

public interface ThemeRepository extends JpaRepository<Theme, Long> {
}
