package phanes.replay.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phanes.replay.review.domain.ReviewLike;

import java.util.Optional;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {

    Optional<ReviewLike> findByUserIdAndReviewId(Long userId, Long reviewId);
}