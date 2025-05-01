package phanes.replay.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import phanes.replay.exception.ReviewNotFountException;
import phanes.replay.review.domain.Review;
import phanes.replay.review.repository.ReviewRepository;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public Long getCountBySuccess(Boolean success) {
        return reviewRepository.countBySuccess(success);
    }

    public Review getReviewById(Long id) {
        return reviewRepository.findById(id).orElseThrow(() -> new ReviewNotFountException("review not found"));
    }

    public void updateReview(Review review) {
        reviewRepository.save(review);
    }
}
