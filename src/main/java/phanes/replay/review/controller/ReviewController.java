package phanes.replay.review.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import phanes.replay.review.dto.request.ReviewCreateRq;
import phanes.replay.review.dto.response.ReviewRs;
import phanes.replay.review.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public List<ReviewRs> reviewList(@RequestParam Long themeId, @RequestParam Integer limit, @RequestParam Integer offset) {
        PageRequest pageRequest = PageRequest.of(offset, limit);
        return reviewService.getReviewByThemeId(themeId, pageRequest);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public void updateReview(@AuthenticationPrincipal Long userId, @ModelAttribute @Valid ReviewCreateRq reviewCreateRq) {
        reviewService.createReview(userId, reviewCreateRq);
    }
}
