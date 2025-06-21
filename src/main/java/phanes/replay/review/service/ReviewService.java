package phanes.replay.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import phanes.replay.common.dto.mapper.PageMapper;
import phanes.replay.common.dto.response.Page;
import phanes.replay.image.service.S3Service;
import phanes.replay.review.domain.Review;
import phanes.replay.review.dto.mapper.ReviewMapper;
import phanes.replay.review.dto.request.ReviewCreateRq;
import phanes.replay.review.dto.request.ReviewUpdateRq;
import phanes.replay.review.dto.response.ReviewRatingRs;
import phanes.replay.review.dto.response.ReviewRs;
import phanes.replay.review.persistence.mapper.ReviewQueryMapper;
import phanes.replay.theme.domain.Theme;
import phanes.replay.theme.service.ThemeQueryService;
import phanes.replay.user.domain.User;
import phanes.replay.user.service.UserQueryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewQueryService reviewQueryService;
    private final PageMapper<List<ReviewRs>> pageMapper;
    private final ReviewQueryMapper reviewQueryMapper;
    private final ThemeQueryService themeQueryService;
    private final UserQueryService userQueryService;
    private final ReviewMapper reviewMapper;
    private final S3Service s3Service;

    public Page<List<ReviewRs>> getReviewByThemeId(Long userId, Long themeId, Integer limit, Integer offset) {
        Long totalCount = reviewQueryService.countByThemeId(themeId);
        List<ReviewRs> data = reviewQueryMapper.findAllByThemeId(userId, themeId, limit, offset)
                .stream()
                .map(reviewMapper::ReviewToReviewDTO)
                .toList();
        return pageMapper.toPage(totalCount, offset, data);
    }

    @Transactional
    public void createReview(Long userId, ReviewCreateRq reviewCreateRq) {
        User user = userQueryService.findById(userId);
        Theme theme = themeQueryService.findById(reviewCreateRq.getThemeId());
        String uploadImage = reviewCreateRq.getImage() == null ? null : s3Service.uploadImage("review/" + UUID.randomUUID() + ".png", reviewCreateRq.getImage());
        Review review = Review.builder()
                .content(reviewCreateRq.getContent())
                .image(uploadImage)
                .success(Boolean.parseBoolean(reviewCreateRq.getSuccess()))
                .score(reviewCreateRq.getRating())
                .hint(reviewCreateRq.getHint())
                .numberOfPlayer(reviewCreateRq.getNumberOfPlayer())
                .themeReview(reviewCreateRq.getThemeReview())
                .storyReview(reviewCreateRq.getStoryReview())
                .levelReview(reviewCreateRq.getLevelReview())
                .user(user)
                .theme(theme)
                .build();
        reviewQueryService.save(review);
    }

    public void updateThemeReview(Long userId, Long reviewId, ReviewUpdateRq reviewUpdateRq) {
        Review review = reviewQueryService.findByIdAndUserId(reviewId, userId);
        review.updateReview(reviewUpdateRq);
        reviewQueryService.save(review);
    }

    public void deleteReview(Long userId, Long reviewId, Long themeId) {
        Review review = reviewQueryService.findByReviewIdAndThemeIdAndUserId(reviewId, themeId, userId);
        reviewQueryService.delete(review);
    }

    public ReviewRatingRs getReviewRatingByThemeId(Long themeId) {
        Object[][] result = reviewQueryService.findCountAndAverageByThemeId(themeId);
        Long totalCount = ((Number) result[0][0]).longValue();
        Double rating = result[0][1] != null ? ((Number) result[0][1]).doubleValue() : 0.0;
        List<Long> scores = toScoreList(reviewQueryService.countAllByThemeId(themeId));
        return ReviewRatingRs.builder()
                .scoreCount(totalCount)
                .averageScore(rating)
                .scores(scores)
                .build();
    }

    private List<Long> toScoreList(List<Object[]> raw) {
        Map<Integer, Long> scoreMap = raw.stream()
                .collect(Collectors.toMap(
                        row -> ((Number) row[0]).intValue(),  // score
                        row -> ((Number) row[1]).longValue()  // count
                ));

        List<Long> scores = new ArrayList<>();
        for (int i = 5; i >= 1; i--) {
            scores.add(scoreMap.getOrDefault(i, 0L));
        }
        return scores;
    }
}
