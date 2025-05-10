package phanes.replay.review.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import phanes.replay.review.domain.Review;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Long countBySuccess(Boolean success);
    Optional<Review> findByIdAndUserId(Long id, Long userId);
    @Query("SELECT r FROM Review r JOIN r.user WHERE r.theme.id = :themeId")
    Page<Review> findAllByThemeId(Long themeId, Pageable pageable);
}
