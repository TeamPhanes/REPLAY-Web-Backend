package phanes.replay.theme.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import phanes.replay.theme.domain.ThemeLike;

import java.util.Optional;

public interface ThemeLikeRepository extends JpaRepository<ThemeLike, Long> {

    Long countByUserId(Long userId);
    @Query("SELECT tl FROM ThemeLike tl JOIN FETCH tl.user u JOIN FETCH tl.theme t WHERE u.id = :userId and t.id = :themeId")
    Optional<ThemeLike> findByUserIdAndThemeId(Long userId, Long themeId);
}
