package phanes.replay.theme.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import phanes.replay.theme.domain.Theme;
import phanes.replay.theme.dto.ThemeListResponse;

import java.util.List;

public interface ThemeRepository extends JpaRepository<Theme, Long>,ThemeRepositoryCustom {

    @Query("SELECT new phanes.replay.theme.dto.ThemeListResponse(" +
            "t.id, t.image, g.name, t.playtime, t.name, t.spot, " +
            "COUNT(r), COALESCE(AVG(r.score), 0.0), " +
            "CASE WHEN COUNT(tl) > 0 THEN true ELSE false END, " +
            "t.level, t.address) " +
            "FROM Theme t " +
            "LEFT JOIN Genre g ON g.theme.id = t.id " +
            "LEFT JOIN Review r ON r.theme.id = t.id " +
            "LEFT JOIN ThemeLike tl ON tl.theme.id = t.id AND tl.user.id = :userId " +
            "WHERE (:keyword IS NULL OR t.name LIKE CONCAT('%',:keyword, '%')) " +
            "AND (:genre IS NULL OR g.name = :genre) " +
            "GROUP BY t.id,t.image, g.name, t.playtime,t.name, t.spot, t.level,t.address")
    List<ThemeListResponse> findThemesByFilter(
            @Param("keyword") String keyword,
            @Param("genre") String genre,
            @Param("userId") Long userId,
            Pageable pageable);

    @Query("SELECT COUNT(DISTINCT t.id) " +
            "FROM Theme t " +
            "LEFT JOIN ParticipatingTheme pt " +
            "LEFT JOIN Genre g " +
            "WHERE (:keyword IS NULL OR t.name LIKE %:keyword%) " +
            "AND (:genre IS NULL OR g.name = :genre)")
    Long countThemesByFilter(@Param("keyword") String keyword,
                             @Param("genre") String genre);
}
