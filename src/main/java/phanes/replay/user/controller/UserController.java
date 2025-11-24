package phanes.replay.user.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import phanes.replay.user.dto.user.ProfileRs;
import phanes.replay.user.dto.user.UserRs;
import phanes.replay.user.service.UserService;

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

    @GetMapping("/profile")
    public ProfileRs myProfile(@AuthenticationPrincipal Long userId) {
        return userService.findProfileById(userId, true);
    }

    @GetMapping("/{userId}")
    public ProfileRs getUser(@PathVariable Long userId) {
        return userService.findProfileById(userId, false);
    }
}