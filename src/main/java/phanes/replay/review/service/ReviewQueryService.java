package phanes.replay.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import phanes.replay.exception.ReviewNotFountException;
import phanes.replay.review.domain.Review;
import phanes.replay.review.persistence.repository.ReviewRepository;

@Service
@RequiredArgsConstructor
public class ReviewQueryService {

    private final ReviewRepository reviewRepository;

    public Long countBySuccess(Boolean success) {
        return reviewRepository.countBySuccess(success);
    }

    public Review findByIdAndUserId(Long id, Long userId) {
        return reviewRepository.findByIdAndUserId(id, userId).orElseThrow(() -> new ReviewNotFountException("review not found"));
    }

    public void save(Review review) {
        reviewRepository.save(review);
    }
}
