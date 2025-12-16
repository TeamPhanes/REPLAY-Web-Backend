package phanes.replay.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phanes.replay.review.domain.ReviewImage;

import java.util.List;
import java.util.Optional;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {

    List<ReviewImage> findAllByReviewId(Long reviewId);

    Optional<ReviewImage> findByIdAndReviewId(Long id, Long reviewId);
}