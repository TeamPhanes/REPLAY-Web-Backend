package phanes.replay.theme.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phanes.replay.theme.domain.ThemeLike;

import java.util.Optional;

public interface ThemeLikeRepository extends JpaRepository<ThemeLike, Long> {

    Long countByUserId(Long userId);
    Optional<ThemeLike> findByUserIdAndThemeId(Long userId, Long themeId);
}
