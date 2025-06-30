package phanes.replay.review.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phanes.replay.review.domain.ReviewLike;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {

    ReviewLike findByReviewIdAndUserId(Long reviewId, Long userId);
}