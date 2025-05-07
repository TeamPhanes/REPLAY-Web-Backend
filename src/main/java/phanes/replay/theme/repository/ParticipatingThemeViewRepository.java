package phanes.replay.theme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phanes.replay.theme.domain.ParticipatingThemeView;

import java.util.List;

public interface ParticipatingThemeViewRepository extends JpaRepository<ParticipatingThemeView, Long> {
    List<ParticipatingThemeView> findByUserId(Long userId);
}
