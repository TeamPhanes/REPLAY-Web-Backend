package phanes.replay.theme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phanes.replay.theme.domain.Genre;

import java.util.List;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    List<Genre> findByThemeId(Long themeId);
}
