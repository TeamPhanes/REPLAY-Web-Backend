package phanes.replay.review.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import phanes.replay.review.domain.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Long countBySuccess(Boolean success);

    Optional<Review> findByIdAndUserId(Long id, Long userId);

    @Query("SELECT r FROM Review r JOIN r.user WHERE r.theme.id = :themeId")
    Page<Review> findAllByThemeId(Long themeId, Pageable pageable);

    @Query("SELECT r FROM Review r JOIN r.user JOIN r.theme WHERE r.id = :reviewId and r.theme.id = :themeId and r.user.id = :userId")
    Optional<Review> findByReviewIdAndThemeIdAndUserId(Long reviewId, Long themeId, Long userId);

    @Query("""
            SELECT COUNT(r), AVG(r.score)
            FROM Review r
            WHERE r.theme.id = :themeId
            """)
    Object[][] findCountAndAverageByThemeId(Long themeId);

    @Query("""
            SELECT r.score, COUNT(*) AS count
            FROM Review r
            WHERE r.theme.id = :themeId
            GROUP BY r.score
            ORDER BY r.score DESC
            """)
    List<Object[]> countAllByThemeId(Long themeId);
}
