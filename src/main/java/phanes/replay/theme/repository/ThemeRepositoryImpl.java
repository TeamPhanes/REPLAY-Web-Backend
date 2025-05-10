package phanes.replay.theme.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import phanes.replay.theme.dto.ThemeListResponse;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ThemeRepositoryImpl implements ThemeRepositoryCustom {
        private final EntityManager em;

    @Override
    public List<ThemeListResponse> searchWithSort(String keyword, String genre, Long userId, String sortBy, Pageable pageable) {
        String jpql = """
            SELECT new phanes.replay.theme.dto.ThemeListResponse(
                t.id, t.image, g.name, t.playtime, t.name, t.spot,
                COUNT(r), COALESCE(AVG(r.score), 0.0),
                CASE WHEN COUNT(tl) > 0 THEN true ELSE false END,
                t.level, t.address)
            FROM Theme t
            LEFT JOIN Genre g ON g.theme.id = t.id
            LEFT JOIN Review r ON r.theme.id = t.id
            LEFT JOIN ThemeLike tl ON tl.theme.id = t.id AND tl.user.id = :userId
            WHERE (:keyword IS NULL OR t.name LIKE CONCAT('%', :keyword, '%'))
            AND (:genre IS NULL OR g.name = :genre)
            GROUP BY t.id, t.image, g.name, t.playtime, t.name, t.spot, t.level, t.address
            """;
        // 🔽 정렬 조건 추가
        if ("score".equalsIgnoreCase(sortBy)) {
            jpql += " ORDER BY AVG(r.score) DESC";
        } else if ("reviewCount".equalsIgnoreCase(sortBy)) {
            jpql += " ORDER BY COUNT(r) DESC";
        } else {
            jpql += " ORDER BY t.id DESC";
        }

        TypedQuery<ThemeListResponse> query = em.createQuery(jpql, ThemeListResponse.class)
                .setParameter("keyword", keyword)
                .setParameter("genre", genre)
                .setParameter("userId", userId);

        // 페이징 처리
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        return query.getResultList();
    }
}
