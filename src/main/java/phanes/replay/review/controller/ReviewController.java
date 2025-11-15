package phanes.replay.review.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import phanes.replay.review.dto.response.ReviewRs;
import phanes.replay.review.service.ReviewService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping(value = "/{themeId}")
    public ReviewRs getThemeReview(@AuthenticationPrincipal Long userId, @PageableDefault Pageable pageable, @PathVariable Long themeId) {
        userId = userId == null ? 0L : userId;
        return reviewService.findAllByThemeId(userId, pageable, themeId);
    }
}