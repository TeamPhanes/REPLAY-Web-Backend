package phanes.replay.review.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import phanes.replay.common.dto.response.Page;
import phanes.replay.review.dto.request.ReviewCreateRq;
import phanes.replay.review.dto.request.ReviewUpdateRq;
import phanes.replay.review.dto.request.ReviewUpdateWithImageRq;
import phanes.replay.review.dto.response.ReviewRatingRs;
import phanes.replay.review.dto.response.ReviewRs;
import phanes.replay.review.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    public Page<List<ReviewRs>> getReviewList(@AuthenticationPrincipal Long userId, @RequestParam Long themeId, @RequestParam Integer limit, @RequestParam Integer offset) {
        userId = userId == null ? 0L : userId;
        return reviewService.findAllByThemeId(userId, themeId, limit, offset);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void createReview(@AuthenticationPrincipal Long userId, @ModelAttribute @Valid ReviewCreateRq reviewCreateRq) {
        reviewService.createReview(userId, reviewCreateRq);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/{reviewId}/like")
    public void likeReview(@AuthenticationPrincipal Long userId, @PathVariable Long reviewId) {
        reviewService.likeReview(userId, reviewId);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping(value = "/{reviewId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void updateThemeReviewWithImage(@AuthenticationPrincipal Long userId, @PathVariable Long reviewId, @ModelAttribute ReviewUpdateWithImageRq reviewUpdateWithImageRq) {
        reviewService.updateThemeReviewWithImage(userId, reviewId, reviewUpdateWithImageRq);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping(value = "/{reviewId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateThemeReview(@AuthenticationPrincipal Long userId, @PathVariable Long reviewId, @RequestBody ReviewUpdateRq reviewUpdateRq) {
        reviewService.updateThemeReview(userId, reviewId, reviewUpdateRq);
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{reviewId}")
    public void deleteReview(@AuthenticationPrincipal Long userId, @PathVariable Long reviewId, @RequestParam Long themeId) {
        reviewService.deleteReview(userId, reviewId, themeId);
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{reviewId}/like")
    public void unlikeReview(@AuthenticationPrincipal Long userId, @PathVariable Long reviewId) {
        reviewService.unlikeReview(userId, reviewId);
    }

    @GetMapping("/rating")
    public ReviewRatingRs getReviewRating(@RequestParam Long themeId) {
        return reviewService.getReviewRatingByThemeId(themeId);
    }
}