package phanes.replay.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import phanes.replay.gathering.repository.GatheringRepository;
import phanes.replay.review.domain.Review;
import phanes.replay.review.domain.ReviewLike;
import phanes.replay.review.dto.ReviewDto;
import phanes.replay.review.dto.response.ReviewCountSummary;
import phanes.replay.review.dto.response.ReviewRs;
import phanes.replay.review.dto.response.ReviewSummary;
import phanes.replay.review.dto.response.UserEvaluation;
import phanes.replay.review.mapper.ReviewMapper;
import phanes.replay.review.repository.ReviewImageJooqRepository;
import phanes.replay.review.repository.ReviewJooqRepository;
import phanes.replay.user.domain.User;
import phanes.replay.user.service.UserQueryService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final UserQueryService userQueryService;
    private final ReviewQueryService reviewQueryService;
    private final ReviewLikeQueryService reviewLikeQueryService;
    private final ReviewJooqRepository reviewJooqRepository;
    private final ReviewImageJooqRepository reviewImageJooqRepository;
    private final GatheringRepository gatheringRepository;
    private final ReviewMapper reviewMapper;

    public ReviewSummary findSummaryByThemeId(Long themeId) {
        Double avgScore = reviewJooqRepository.aggregateByThemeId(themeId);
        Long createdGatheringCount = gatheringRepository.countByThemeId(themeId);
        UserEvaluation userEvaluation = reviewJooqRepository.findEvaluationByThemeId(themeId);
        ReviewCountSummary reviewCountSummary = reviewJooqRepository.findScoreCountByThemeId(themeId);
        return reviewMapper.toReviewSummary(avgScore, createdGatheringCount, reviewCountSummary, userEvaluation);
    }

    public Page<ReviewRs> findAllByThemeId(Long userId, Pageable pageable, Long themeId) {
        Page<ReviewDto> reviewDetailList = reviewJooqRepository.findAllByThemeId(userId, pageable, themeId);
        List<Long> reviewIdList = reviewDetailList.stream().map(ReviewDto::getId).toList();
        Map<Long, List<String>> reviewImageListMap = reviewImageJooqRepository.findAllByReviewIdList(reviewIdList);
        List<ReviewRs> contents = reviewDetailList.stream().map(r -> reviewMapper.toReviewRs(r, reviewImageListMap.getOrDefault(r.getId(), Collections.emptyList()))).toList();
        return new PageImpl<>(contents, pageable, reviewDetailList.getTotalElements());
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