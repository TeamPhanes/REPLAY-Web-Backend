package phanes.replay.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phanes.replay.review.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
