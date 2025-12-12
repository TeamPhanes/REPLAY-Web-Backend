package phanes.replay.review.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import phanes.replay.annotation.ValidateFileExtension;
import phanes.replay.annotation.ValidateFileSize;
import phanes.replay.annotation.ValidateImageFile;
import phanes.replay.review.dto.request.ReviewRq;
import phanes.replay.review.dto.request.ReviewUpdateRq;
import phanes.replay.review.dto.response.ReviewRs;
import phanes.replay.review.dto.response.ReviewSummary;
import phanes.replay.review.service.ReviewService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/summary/{themeId}")
    public ReviewSummary getThemeReviewSummary(@PathVariable Long themeId) {
        return reviewService.findSummaryByThemeId(themeId);
    }

    @GetMapping(value = "/{themeId}")
    public Page<ReviewRs> getThemeReview(@AuthenticationPrincipal Long userId, @PageableDefault Pageable pageable, @PathVariable Long themeId) {
        userId = userId == null ? 0L : userId;
        return reviewService.findAllByThemeId(userId, pageable, themeId);
    }

    @PostMapping(value = "/{themeId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void saveReview(@AuthenticationPrincipal Long userId,
                           @PathVariable Long themeId,
                           @RequestPart(value = "review") ReviewRq reviewRq,
                           @RequestPart(required = false) @ValidateImageFile @ValidateFileSize @ValidateFileExtension List<MultipartFile> images) {
        reviewService.save(userId, themeId, reviewRq, images);
    }

    @PostMapping("/like/{reviewId}")
    public void likeReview(@AuthenticationPrincipal Long userId, @PathVariable Long reviewId) {
        reviewService.saveReviewLike(userId, reviewId);
    }

    @PutMapping(value = "/{reviewId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateReview(@AuthenticationPrincipal Long userId, @PathVariable Long reviewId, @RequestBody ReviewUpdateRq reviewUpdateRq, @RequestParam Map<String, MultipartFile> images) {
        reviewService.updateReview(userId, reviewId, reviewUpdateRq, images);
    }

    @DeleteMapping("/{reviewId}")
    public void deleteReview(@AuthenticationPrincipal Long userId, @PathVariable Long reviewId) {
        reviewService.deleteReview(userId, reviewId);
    }

    @DeleteMapping("/like/{reviewId}")
    public void unLikeReview(@AuthenticationPrincipal Long userId, @PathVariable Long reviewId) {
        reviewService.deleteReviewLike(userId, reviewId);
    }
}