package phanes.replay.theme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phanes.replay.theme.domain.ThemeWithGenreView;

public interface ThemeWithGenreViewRepository extends JpaRepository<ThemeWithGenreView, Long> {
}
