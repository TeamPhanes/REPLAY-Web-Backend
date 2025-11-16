package phanes.replay.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import phanes.replay.review.domain.ReviewLike;
import phanes.replay.review.repository.ReviewLikeRepository;

@Service
@RequiredArgsConstructor
public class ReviewLikeQueryService {

    private final ReviewLikeRepository reviewLikeRepository;

    public void save(ReviewLike reviewLike) {
        reviewLikeRepository.save(reviewLike);
    }

    public void delete(ReviewLike reviewLike) {
        reviewLikeRepository.delete(reviewLike);
    }

    public ReviewLike findByUserIdAndReviewId(Long userId, Long reviewId) {
        return reviewLikeRepository.findByUserIdAndReviewId(userId, reviewId)
                .orElseThrow();
    }
}