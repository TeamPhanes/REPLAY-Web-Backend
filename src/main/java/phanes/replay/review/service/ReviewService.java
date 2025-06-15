package phanes.replay.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import phanes.replay.common.dto.mapper.PageMapper;
import phanes.replay.common.dto.response.Page;
import phanes.replay.exception.ImageUploadFailException;
import phanes.replay.image.service.S3Service;
import phanes.replay.review.domain.Review;
import phanes.replay.review.domain.ReviewImage;
import phanes.replay.review.dto.mapper.ReviewMapper;
import phanes.replay.review.dto.request.ReviewCreateRq;
import phanes.replay.review.dto.request.ReviewUpdateRq;
import phanes.replay.review.dto.response.ReviewRatingRs;
import phanes.replay.review.dto.response.ReviewRs;
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

    private final ReviewImageQueryService reviewImageQueryService;
    private final ReviewQueryService reviewQueryService;
    private final PageMapper<List<ReviewRs>> pageMapper;
    private final ThemeQueryService themeQueryService;
    private final UserQueryService userQueryService;
    private final ReviewMapper reviewMapper;
    private final S3Service s3Service;

    public Page<List<ReviewRs>> getReviewByThemeId(Long themeId, Pageable pageable) {
        Long totalCount = reviewQueryService.countByThemeId(themeId);
        List<ReviewRs> data = reviewQueryService.findAllByThemeId(themeId, pageable).stream().map(reviewMapper::ReviewToReviewDTO).toList();
        return pageMapper.toPage(totalCount, pageable.getOffset(), data);
    }

    @Transactional
    public void createReview(Long userId, ReviewCreateRq reviewCreateRq) {
        User user = userQueryService.findById(userId);
        Theme theme = themeQueryService.findById(reviewCreateRq.getThemeId());
        Review review = Review.builder()
                .content(reviewCreateRq.getContent())
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

        if (reviewCreateRq.getImages() != null) {
            List<String> uploadImageList = new ArrayList<>();
            try {
                for (MultipartFile image : reviewCreateRq.getImages()) {
                    String uploadImage = s3Service.uploadImage("review/" + UUID.randomUUID() + ".png", image);
                    uploadImageList.add(uploadImage);
                    ReviewImage reviewImage = ReviewImage.builder()
                            .url(uploadImage)
                            .review(review)
                            .build();
                    reviewImageQueryService.save(reviewImage);
                }
            } catch (Exception e) {
                for (String uploadImage : uploadImageList) {
                    s3Service.deleteImage("replay", uploadImage);
                }
                throw new ImageUploadFailException("image upload failed", e);
            }
        }
    }

    public void updateThemeReview(Long userId, Long reviewId, ReviewUpdateRq reviewUpdateRq) {
        User user = userQueryService.findById(userId);
        Review review = reviewQueryService.findByIdAndUserId(reviewId, user.getId());
        review.updateReview(reviewUpdateRq);
        reviewQueryService.save(review);
    }

    public void deleteReview(Long userId, Long reviewId, Long themeId) {
        Review review = reviewQueryService.findByReviewIdAndThemeIdAndUserId(reviewId, themeId, userId);
        List<ReviewImage> reviewImageList = reviewImageQueryService.findByReviewId(reviewId);
        reviewImageQueryService.deleteAll(reviewImageList);
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
