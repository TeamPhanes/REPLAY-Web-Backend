package phanes.replay.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phanes.replay.review.domain.ReviewImage;

import java.util.List;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Integer> {

    List<ReviewImage> findAllByReviewId(Long reviewId);
}