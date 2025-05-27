package phanes.replay.theme.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phanes.replay.theme.domain.ThemeLike;

public interface ThemeLikeRepository extends JpaRepository<ThemeLike, Long> {

    Long countByUserId(Long userId);
}
