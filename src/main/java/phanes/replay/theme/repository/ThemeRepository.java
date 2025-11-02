package phanes.replay.theme.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import phanes.replay.theme.domain.Theme;

public interface ThemeRepository extends JpaRepository<Theme, Long> {

    Slice<Theme> findAllBy(Pageable pageable);
}