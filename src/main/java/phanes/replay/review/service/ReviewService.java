package phanes.replay.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import phanes.replay.gathering.repository.GatheringRepository;
import phanes.replay.review.domain.Review;
import phanes.replay.review.domain.ReviewLike;
import phanes.replay.review.dto.response.ReviewCountStat;
import phanes.replay.review.dto.response.ReviewDetailRs;
import phanes.replay.review.dto.response.ReviewRs;
import phanes.replay.review.dto.response.UserEvaluation;
import phanes.replay.review.mapper.ReviewMapper;
import phanes.replay.review.repository.ReviewJooqRepository;
import phanes.replay.user.domain.User;
import phanes.replay.user.service.UserQueryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final UserQueryService userQueryService;
    private final ReviewQueryService reviewQueryService;
    private final ReviewLikeQueryService reviewLikeQueryService;
    private final ReviewJooqRepository reviewJooqRepository;
    private final GatheringRepository gatheringRepository;
    private final ReviewMapper reviewMapper;

    public ReviewRs findAllByThemeId(Long userId, Pageable pageable, Long themeId) {
        List<ReviewDetailRs> reviewDetailList = reviewJooqRepository.findAllByThemeId(userId, pageable, themeId);
        Double avgScore = reviewJooqRepository.aggregateByThemeId(themeId);
        Long createdGatheringCount = gatheringRepository.countByThemeId(themeId);
        UserEvaluation userEvaluation = reviewJooqRepository.findEvaluationByThemeId(themeId);
        List<ReviewCountStat> reviewCount = reviewJooqRepository.findScoreCountByThemeId(themeId);
        return reviewMapper.toReviewRs(avgScore, createdGatheringCount, reviewCount, userEvaluation, reviewDetailList);
    }

    public void saveReviewLike(Long userId, Long reviewId) {
        User user = userQueryService.findById(userId);
        Review review = reviewQueryService.findById(reviewId);
        ReviewLike reviewLike = ReviewLike.builder()
                .user(user)
                .review(review)
                .build();
        reviewLikeQueryService.save(reviewLike);
    }

    public void deleteReviewLike(Long userId, Long reviewId) {
        ReviewLike reviewLike = reviewLikeQueryService.findByUserIdAndReviewId(userId, reviewId);
        reviewLikeQueryService.delete(reviewLike);
    }
}