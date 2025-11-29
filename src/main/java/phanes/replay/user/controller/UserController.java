package phanes.replay.user.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import phanes.replay.user.dto.user.*;
import phanes.replay.user.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/me")
    public UserRs me(@AuthenticationPrincipal Long userId) {
        return userService.findByUserId(userId);
    }

    @GetMapping("/me/profile")
    public ProfileRs myProfile(@AuthenticationPrincipal Long userId) {
        return userService.findProfileById(userId, true);
    }

    @GetMapping("/{userId}")
    public ProfileRs getUser(@PathVariable Long userId) {
        return userService.findProfileById(userId, false);
    }

    @GetMapping("/me/comment")
    public List<MyCommentRs> myComment(@AuthenticationPrincipal Long userId, @PageableDefault Pageable pageable) {
        return userService.findCommentById(userId, pageable);
    }

    @GetMapping("/me/theme/visit")
    public Page<MyVisitThemeRs> myVisitTheme(@AuthenticationPrincipal Long userId, @PageableDefault Pageable pageable) {
        return userService.findVisitThemeById(userId, pageable);
    }

    @GetMapping("/me/gathering/visit")
    public Page<MyParticipantGatheringRs> myParticipantGathering(@AuthenticationPrincipal Long userId, @PageableDefault Pageable pageable) {
        return userService.findParticipantGatheringById(userId, pageable);
    }
}