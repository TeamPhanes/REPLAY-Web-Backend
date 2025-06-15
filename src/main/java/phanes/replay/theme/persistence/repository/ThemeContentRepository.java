package phanes.replay.theme.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import phanes.replay.theme.domain.ThemeContent;

import java.util.Optional;

public interface ThemeContentRepository extends JpaRepository<ThemeContent, Long> {

    @Query("SELECT tc FROM ThemeContent tc JOIN FETCH tc.theme t WHERE t.id = :themeId")
    Optional<ThemeContent> findByThemeId(Long themeId);
}
