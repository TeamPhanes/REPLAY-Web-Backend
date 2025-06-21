package phanes.replay.review.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import phanes.replay.review.domain.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Long countBySuccess(Boolean success);

    @EntityGraph(attributePaths = {"user", "theme"})
    Optional<Review> findByIdAndUserId(Long id, Long userId);

    @EntityGraph(attributePaths = {"user", "theme"})
    Page<Review> findAllByThemeId(Long themeId, Pageable pageable);

    @Query("SELECT r FROM Review r JOIN FETCH r.user u JOIN FETCH r.theme t WHERE r.id = :reviewId and t.id = :themeId and u.id = :userId")
    Optional<Review> findByReviewIdAndThemeIdAndUserId(Long reviewId, Long themeId, Long userId);

    @Query("SELECT COUNT(r), AVG(r.score) FROM Review r JOIN r.theme t WHERE t.id = :themeId")
    Object[][] findCountAndAverageByThemeId(Long themeId);

    @Query("SELECT r.score, COUNT(*) AS count FROM Review r JOIN r.theme t WHERE t.id = :themeId GROUP BY r.score ORDER BY r.score DESC")
    List<Object[]> countAllByThemeId(Long themeId);

    Long countByThemeId(Long themeId);
}
