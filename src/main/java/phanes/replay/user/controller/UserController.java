package phanes.replay.user.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import phanes.replay.common.dto.response.Page;
import phanes.replay.user.dto.user.request.UserUpdateRq;
import phanes.replay.user.dto.user.response.*;
import phanes.replay.user.service.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/me")
    public UserRs me(@AuthenticationPrincipal Long userId) {
        return userService.getProfileUserInfo(userId);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PatchMapping(value = "/me", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateMe(@AuthenticationPrincipal Long userId, @ModelAttribute @Valid UserUpdateRq userUpdateRq) {
        userService.updateUser(userId, userUpdateRq.getImage(), userUpdateRq.getNickname(), userUpdateRq.getComment(), Boolean.parseBoolean(userUpdateRq.getEmailMark()), Boolean.parseBoolean(userUpdateRq.getGenderMark()));
    }

    @GetMapping("/{nickname}")
    public OtherUserRs getOtherUser(@PathVariable String nickname) {
        return userService.getUserByNickname(nickname);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/me/schedule")
    public Map<LocalDate, List<UserScheduleRs>> mySchedule(@AuthenticationPrincipal Long userId) {
        return userService.getMySchedule(userId);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/me/theme")
    public Page<List<UserVisitThemeRs>> myVisitTheme(@AuthenticationPrincipal Long userId, @RequestParam("limit") Integer limit, @RequestParam("offset") Integer offset) {
        return userService.getMyVisitTheme(userId, limit, offset);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/me/gathering")
    public Page<List<UserParticipatingGatheringRs>> myParticipatingGathering(@AuthenticationPrincipal Long userId, @RequestParam("limit") Integer limit, @RequestParam("offset") Integer offset) {
        return userService.getMyParticipatingGathering(userId, limit, offset);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/me/gathering/like")
    public Page<List<UserLikeGatheringRs>> myLikeGathering(@AuthenticationPrincipal Long userId, @RequestParam("limit") Integer limit, @RequestParam("offset") Integer offset) {
        return userService.getMyLikeGathering(userId, limit, offset);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/me/theme/like")
    public Page<List<UserLikeThemeRs>> myLikeTheme(@AuthenticationPrincipal Long userId, @RequestParam("limit") Integer limit, @RequestParam("offset") Integer offset) {
        return userService.getMyLikeTheme(userId, limit, offset);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/me/comment")
    public Page<Map<LocalDate, List<UserCommentRs>>> myComment(@AuthenticationPrincipal Long userId, @RequestParam("sortBy") String sortBy, @RequestParam("limit") Integer limit, @RequestParam("offset") Integer offset) {
        Sort.Direction direction = "create".equals(sortBy) ? Sort.Direction.ASC : Sort.Direction.DESC;
        PageRequest pageRequest = PageRequest.of(offset, limit, Sort.by(direction, "createdAt"));
        return userService.getMyComment(userId, pageRequest);
    }
}