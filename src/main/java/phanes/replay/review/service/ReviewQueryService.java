package phanes.replay.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import phanes.replay.exception.ReviewNotFountException;
import phanes.replay.review.domain.Review;
import phanes.replay.review.persistence.repository.ReviewRepository;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReviewQueryService {

    private final ReviewRepository reviewRepository;

    public Long countByUserIdAndSuccess(Long userId, Boolean success) {
        return reviewRepository.countByUserIdAndSuccess(userId, success);
    }

    public Long countByThemeId(Long themeId) {
        return reviewRepository.countByThemeId(themeId);
    }

    public List<Object[]> countAllByThemeId(Long themeId) {
        return reviewRepository.countAllByThemeId(themeId);
    }

    public Review findById(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFountException("Review  not found", Map.of("reviewId", reviewId)));
    }

    public Review findByIdAndUserId(Long id, Long userId) {
        return reviewRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ReviewNotFountException("Review  not found", Map.of("userId", userId, "reviewId", id)));
    }

    public Review findByReviewIdAndThemeIdAndUserId(Long reviewId, Long themeId, Long userId) {
        return reviewRepository.findByReviewIdAndThemeIdAndUserId(reviewId, themeId, userId)
                .orElseThrow(() -> new ReviewNotFountException("Review not found", Map.of("reviewId", reviewId, "themeId", themeId, "userId", userId)));
    }

    public Object[][] findCountAndAverageByThemeId(Long themeId) {
        return reviewRepository.findCountAndAverageByThemeId(themeId);
    }

    public void save(Review review) {
        reviewRepository.save(review);
    }

    public void delete(Review review) {
        reviewRepository.delete(review);
    }
}
