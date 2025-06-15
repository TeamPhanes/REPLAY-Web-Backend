package phanes.replay.review.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phanes.replay.review.domain.ReviewImage;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {
}
