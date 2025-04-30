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
        StringBuilder jpql = new StringBuilder();
        jpql.append("SELECT new phanes.replay.theme.dto.ThemeListResponse(")
                .append("t.id, t.image, g.name, t.playtime, t.name, t.spot, ")
                .append("COUNT(r), COALESCE(AVG(r.score), 0.0), ")
                .append("CASE WHEN COUNT(tl) > 0 THEN true ELSE false END, ")
                .append("t.level, t.address) ")
                .append("FROM Theme t ")
                .append("LEFT JOIN Genre g ON g.theme.id = t.id ")
                .append("LEFT JOIN Review r ON r.theme.id = t.id ")
                .append("LEFT JOIN ThemeLike tl ON tl.theme.id = t.id AND tl.user.id = :userId ")
                .append("WHERE (:keyword IS NULL OR t.name LIKE CONCAT('%', :keyword, '%')) ")
                .append("AND (:genre IS NULL OR g.name = :genre) ")
                .append("GROUP BY t.id, t.image, g.name, t.playtime, t.name, t.spot, t.level, t.address ");

        // 🔽 정렬 조건 추가
        switch (sortBy) {
            case "rating":
                jpql.append("ORDER BY AVG(r.score) DESC ");
                break;
            case "likes":
                jpql.append("ORDER BY COUNT(tl) DESC ");
                break;
            case "reviews":
                jpql.append("ORDER BY COUNT(r) DESC ");
                break;
            default:
                jpql.append("ORDER BY t.id DESC ");
        }

        TypedQuery<ThemeListResponse> query = em.createQuery(jpql.toString(), ThemeListResponse.class);
        query.setParameter("keyword", keyword);
        query.setParameter("genre", genre);
        query.setParameter("userId", userId);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        return query.getResultList();
    }
}
