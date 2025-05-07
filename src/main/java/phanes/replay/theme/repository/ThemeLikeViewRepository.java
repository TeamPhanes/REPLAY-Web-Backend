package phanes.replay.theme.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import phanes.replay.theme.domain.ThemeLikeView;

import java.util.List;

public interface ThemeLikeViewRepository extends JpaRepository<ThemeLikeView, Long> {

    List<ThemeLikeView> findByUserId(Long userId, Pageable pageable);
}
