package phanes.replay.theme.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import phanes.replay.theme.domain.ThemeVisit;

import java.util.Optional;

@Repository
public interface ThemeVisitRepository extends JpaRepository<ThemeVisit, Long> {
    Long countByUserId(Long userId);

    @Query("SELECT tv FROM ThemeVisit tv JOIN FETCH tv.user u JOIN FETCH tv.theme t WHERE u.id = :userId AND t.id = :themeId")
    Optional<ThemeVisit> findByUserIdAndThemeId(Long userId, Long themeId);
}
