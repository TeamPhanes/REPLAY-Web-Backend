package phanes.replay.theme.repository;

import org.springframework.data.domain.Pageable;
import phanes.replay.theme.dto.ThemeListResponse;
import java.util.List;

public interface ThemeRepositoryCustom {
    List<ThemeListResponse> searchWithSort(String keyword, String genre, Long userId, String sortBy, Pageable pageable);

}
