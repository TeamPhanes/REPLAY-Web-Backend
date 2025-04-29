package phanes.replay.theme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import phanes.replay.theme.domain.ParticipatingTheme;

@Repository
public interface ParticipatingThemeRepository extends JpaRepository<ParticipatingTheme, Long> {
    Long countByUserId(Long userId);
}
