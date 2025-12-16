package phanes.replay.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import phanes.replay.gathering.repository.GatheringRepository;
import phanes.replay.review.domain.Review;
import phanes.replay.review.domain.ReviewImage;
import phanes.replay.review.domain.ReviewLike;
import phanes.replay.review.dto.ReviewDto;
import phanes.replay.review.dto.ReviewImageDto;
import phanes.replay.review.dto.request.ReviewRq;
import phanes.replay.review.dto.request.ReviewUpdateRq;
import phanes.replay.review.dto.response.ReviewCountSummary;
import phanes.replay.review.dto.response.ReviewRs;
import phanes.replay.review.dto.response.ReviewSummary;
import phanes.replay.review.dto.response.UserEvaluation;
import phanes.replay.review.mapper.ReviewMapper;
import phanes.replay.review.repository.ReviewImageJooqRepository;
import phanes.replay.review.repository.ReviewJooqRepository;
import phanes.replay.s3.repository.S3Repository;
import phanes.replay.theme.domain.ThemeVisit;
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
        Map<Long, List<ReviewImageDto>> reviewImageListMap = reviewImageJooqRepository.findAllByReviewIdList(reviewIdList);
        List<ReviewRs> contents = reviewDetailList.stream().map(r -> reviewMapper.toReviewRs(r, reviewImageListMap.getOrDefault(r.getId(), Collections.emptyList()))).toList();
        return new PageImpl<>(contents, pageable, reviewDetailList.getTotalElements());
    }

    @Transactional
    public void save(Long userId, Long themeId, ReviewRq reviewRq, Map<String, MultipartFile> images) {
        User user = userQueryService.findById(userId);
        ThemeVisit themeVisit = themeVisitQueryService.findByUserIdAndThemeId(user.getId(), themeId);
        Review review = Review.builder()
                .user(user)
                .themeVisit(themeVisit)
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

        themeVisit.updateVisitDate(reviewRq.getDate());
        themeVisitQueryService.save(themeVisit);

        images.remove("review");
        if (!images.isEmpty()) {
            List<ReviewImage> savedImages = new ArrayList<>();
            for (String key : images.keySet()) {
                MultipartFile image = images.get(key);
                String extension = FileUtils.getExtension(image.getOriginalFilename());
                String uploadImage = s3Repository.uploadImage("review/" + UUID.randomUUID() + "." + extension, image);
                savedImages.add(ReviewImage.builder()
                        .review(savedReview)
                        .image(uploadImage)
                        .isRepresentative(key.equals(reviewRq.getRepresentativeId()))
                        .build());
            }
            reviewImageQueryService.saveAll(savedImages);
        }
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

    @Transactional
    public void updateReview(Long userId, Long reviewId, ReviewUpdateRq reviewUpdateRq, Map<String, MultipartFile> images) {
        Review review = reviewQueryService.findByIdAndUserId(reviewId, userId);
        review.update(reviewUpdateRq);
        reviewQueryService.save(review);

        ThemeVisit themeVisit = review.getThemeVisit();
        themeVisit.updateVisitDate(reviewUpdateRq.getDate());
        themeVisitQueryService.save(themeVisit);

        if (!reviewUpdateRq.getDeleteImageIds().isEmpty()) {
            List<ReviewImage> deleteReviewImageList = reviewImageQueryService.findAll(reviewUpdateRq.getDeleteImageIds());
            reviewImageQueryService.deleteAll(deleteReviewImageList);
            for (ReviewImage reviewImage : deleteReviewImageList) {
                String fileName = s3Repository.extractPathAfterBucket(reviewImage.getImage());
                s3Repository.deleteImage(fileName);
            }
        }

        List<ReviewImage> reviewImageList = reviewImageQueryService.findAllByReviewId(reviewId);
        for (ReviewImage reviewImage : reviewImageList) {
            if (String.valueOf(reviewImage.getId()).equals(reviewUpdateRq.getRepresentativeId())) {
                reviewImage.updateRepresentative(true);
                reviewImageQueryService.save(reviewImage);
            }
        }

        images.remove("review");
        if (!images.isEmpty()) {
            List<ReviewImage> savedImages = new ArrayList<>();
            for (String key : images.keySet()) {
                MultipartFile image = images.get(key);
                String extension = FileUtils.getExtension(image.getOriginalFilename());
                String uploadImage = s3Repository.uploadImage("review/" + UUID.randomUUID() + "." + extension, image);
                savedImages.add(ReviewImage.builder()
                        .review(review)
                        .image(uploadImage)
                        .isRepresentative(key.equals(reviewUpdateRq.getRepresentativeId()))
                        .build());
            }
            reviewImageQueryService.saveAll(savedImages);
        }
    }

    public void deleteReviewLike(Long userId, Long reviewId) {
        ReviewLike reviewLike = reviewLikeQueryService.findByUserIdAndReviewId(userId, reviewId);
        reviewLikeQueryService.delete(reviewLike);
    }

    @Transactional
    public void deleteReview(Long userId, Long reviewId) {
        Review review = reviewQueryService.findByIdAndUserId(reviewId, userId);
        ThemeVisit themeVisit = review.getThemeVisit();
        List<ReviewLike> reviewLikeList = reviewLikeQueryService.findAllByReviewId(reviewId);
        List<ReviewImage> reviewImageList = reviewImageQueryService.findAllByReviewId(reviewId);
        reviewLikeQueryService.deleteAll(reviewLikeList);
        reviewImageQueryService.deleteAll(reviewImageList);
        reviewQueryService.delete(review);
        themeVisit.updateVisitDate(null);
        themeVisitQueryService.save(themeVisit);
    }
}