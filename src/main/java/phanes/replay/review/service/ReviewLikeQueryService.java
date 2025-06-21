package phanes.replay.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import phanes.replay.review.domain.ReviewLike;
import phanes.replay.review.persistence.repository.ReviewLikeRepository;

@Service
@RequiredArgsConstructor
public class ReviewLikeQueryService {

    private final ReviewLikeRepository reviewLikeRepository;

    public ReviewLike findByReviewIdAndUserId(Long reviewId, Long userId) {
        return reviewLikeRepository.findByReviewIdAndUserId(reviewId, userId);
    }

    public void save(ReviewLike reviewLike) {
        reviewLikeRepository.save(reviewLike);
    }

    public void delete(ReviewLike reviewLike) {
        reviewLikeRepository.delete(reviewLike);
    }
}
