package phanes.replay.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import phanes.replay.common.s3.S3Repository;
import phanes.replay.gathering.repository.GatheringRepository;
import phanes.replay.review.domain.Review;
import phanes.replay.review.domain.ReviewImage;
import phanes.replay.review.domain.ReviewLike;
import phanes.replay.review.dto.ReviewDto;
import phanes.replay.review.dto.request.ReviewRq;
import phanes.replay.review.dto.response.ReviewCountSummary;
import phanes.replay.review.dto.response.ReviewRs;
import phanes.replay.review.dto.response.ReviewSummary;
import phanes.replay.review.dto.response.UserEvaluation;
import phanes.replay.review.mapper.ReviewMapper;
import phanes.replay.review.repository.ReviewImageJooqRepository;
import phanes.replay.review.repository.ReviewJooqRepository;
import phanes.replay.theme.domain.Theme;
import phanes.replay.theme.domain.ThemeVisit;
import phanes.replay.theme.service.ThemeQueryService;
import phanes.replay.theme.service.ThemeVisitQueryService;
import phanes.replay.user.domain.User;
import phanes.replay.user.service.UserQueryService;
import phanes.replay.utils.FileUtils;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final UserQueryService userQueryService;
    private final ReviewQueryService reviewQueryService;
    private final ReviewLikeQueryService reviewLikeQueryService;
    private final ReviewImageQueryService reviewImageQueryService;
    private final ThemeQueryService themeQueryService;
    private final ThemeVisitQueryService themeVisitQueryService;
    private final ReviewJooqRepository reviewJooqRepository;
    private final ReviewImageJooqRepository reviewImageJooqRepository;
    private final GatheringRepository gatheringRepository;
    private final S3Repository s3Repository;
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

    @Transactional
    public void save(Long userId, Long themeId, ReviewRq reviewRq, List<MultipartFile> images) {
        User user = userQueryService.findById(userId);
        Theme theme = themeQueryService.findById(themeId);
        Review review = Review.builder()
                .user(user)
                .theme(theme)
                .score(reviewRq.getScore())
                .themeReview(reviewRq.getThemeReview())
                .levelReview(reviewRq.getLevelReview())
                .storyReview(reviewRq.getStoryReview())
                .isSuccess(reviewRq.getIsSuccess())
                .numberOfPlayer(reviewRq.getNumberOfPlayer())
                .hint(reviewRq.getHint())
                .content(reviewRq.getContent())
                .build();
        Review savedReview = reviewQueryService.save(review);

        ThemeVisit themeVisit = themeVisitQueryService.findByUserIdAndThemeId(userId, themeId);
        themeVisit.updateVisitDate(reviewRq.getDate());
        themeVisitQueryService.save(themeVisit);

        List<ReviewImage> savedImages = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            MultipartFile image = images.get(i);
            String extension = FileUtils.getExtension(Objects.requireNonNull(image.getOriginalFilename())).toLowerCase(Locale.KOREA);
            String uploadImage = s3Repository.uploadImage("review/" + UUID.randomUUID() + "." + extension, image);
            savedImages.add(ReviewImage.builder()
                    .user(user)
                    .review(savedReview)
                    .image(uploadImage)
                    .isRepresentative(reviewRq.getRepresentativeImageCount() == i)
                    .build());
        }
        reviewImageQueryService.saveAll(savedImages);
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