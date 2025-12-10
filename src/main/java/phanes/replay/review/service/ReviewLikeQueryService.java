package phanes.replay.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import phanes.replay.review.domain.ReviewLike;
import phanes.replay.review.repository.ReviewLikeRepository;

import java.util.List;

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

    public List<ReviewLike> findAllByReviewId(Long reviewId) {
        return reviewLikeRepository.findAllByReviewId(reviewId);
    }

    public void deleteAll(List<ReviewLike> reviewLikeList) {
        reviewLikeRepository.deleteAll(reviewLikeList);
    }
}