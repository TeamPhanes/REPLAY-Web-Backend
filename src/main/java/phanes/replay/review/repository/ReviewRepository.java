package phanes.replay.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phanes.replay.review.domain.Review;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Long countBySuccess(Boolean success);
    Optional<Review> findByIdAndUserId(Long id, Long userId);
}
