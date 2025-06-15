package phanes.replay.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import phanes.replay.exception.ReviewNotFountException;
import phanes.replay.review.domain.Review;
import phanes.replay.review.persistence.repository.ReviewRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewQueryService {

    private final ReviewRepository reviewRepository;

    public Long countBySuccess(Boolean success) {
        return reviewRepository.countBySuccess(success);
    }

    public Page<Review> findAllByThemeId(Long themeId, Pageable pageable) {
        return reviewRepository.findAllByThemeId(themeId, pageable);
    }

    public Review findByIdAndUserId(Long id, Long userId) {
        return reviewRepository.findByIdAndUserId(id, userId).orElseThrow(() -> new ReviewNotFountException(String.format("Review  not found - user: %d, review: %d", userId, id)));
    }

    public Review findByReviewIdAndThemeIdAndUserId(Long reviewId, Long themeId, Long userId) {
        return reviewRepository.findByReviewIdAndThemeIdAndUserId(reviewId, themeId, userId).orElseThrow(() -> new ReviewNotFountException(String.format("user: %d, theme: %d, review: %d not found", userId, themeId, reviewId)));
    }

    public Long countByThemeId(Long themeId) {
        return reviewRepository.countByThemeId(themeId);
    }

    public Object[][] findCountAndAverageByThemeId(Long themeId) {
        return reviewRepository.findCountAndAverageByThemeId(themeId);
    }

    public List<Object[]> countAllByThemeId(Long themeId) {
        return reviewRepository.countAllByThemeId(themeId);
    }

    public void save(Review review) {
        reviewRepository.save(review);
    }

    public void delete(Review review) {
        reviewRepository.delete(review);
    }
}
