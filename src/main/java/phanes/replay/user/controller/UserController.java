package phanes.replay.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import phanes.replay.user.controller.request.UpdateMeRequest;
import phanes.replay.user.dto.*;
import phanes.replay.user.service.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public UserDTO me(@AuthenticationPrincipal Long userId) {
        return userService.getUser(userId);
    }

    @PatchMapping(value = "/me", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateMe(@AuthenticationPrincipal Long userId, @ModelAttribute @Valid UpdateMeRequest updateMeRequest) {
        userService.updateUser(userId, updateMeRequest.getImage(), updateMeRequest.getNickname(), updateMeRequest.getComment(), Boolean.parseBoolean(updateMeRequest.getEmailMark()), Boolean.parseBoolean(updateMeRequest.getGenderMark()));
    }

    @GetMapping("/{nickname}")
    public OtherUserDTO getOtherUser(@PathVariable String nickname) {
        return userService.getUserByNickname(nickname);
    }

    @GetMapping("/me/schedule")
    public Map<LocalDate, List<UserScheduleDTO>> mySchedule(@AuthenticationPrincipal Long userId) {
        return userService.getMySchedule(userId);
    }

    @GetMapping("/me/theme")
    public List<UserPlayThemeDTO> myPlayTheme(@AuthenticationPrincipal Long userId) {
        return userService.getMyPlayingTheme(userId);
    }

    @PatchMapping("/me/theme")
    public void updateMyPlayTheme(@AuthenticationPrincipal Long userId, @RequestBody UserPlayThemeDTO theme) {
        userService.updateThemeReview(userId, theme);
    }

    @GetMapping("/me/gathering")
    public List<UserParticipatingGatheringDTO> myParticipatingGathering(@AuthenticationPrincipal Long userId, @RequestParam("limit") Integer limit, @RequestParam("offset") Integer offset) {
        PageRequest pageRequest = PageRequest.of(offset, limit);
        return userService.getMyParticipatingGathering(userId, pageRequest);
    }

    @GetMapping("/me/gathering/like")
    public List<UserLikeGatheringDTO> myLikeGathering(@AuthenticationPrincipal Long userId, @RequestParam("limit") Integer limit, @RequestParam("offset") Integer offset) {
        PageRequest pageRequest = PageRequest.of(offset, limit);
        return userService.getMyLikeGathering(userId, pageRequest);
    }

    @GetMapping("/me/theme/like")
    public List<UserLikeThemeDTO> myLikeTheme(@AuthenticationPrincipal Long userId, @RequestParam("limit") Integer limit, @RequestParam("offset") Integer offset) {
        PageRequest pageRequest = PageRequest.of(offset, limit);
        return userService.getMyLikeTheme(userId, pageRequest);
    }

    @GetMapping("/me/comment")
    public Map<LocalDate, List<UserCommentDTO>> myComment(@AuthenticationPrincipal Long userId, @RequestParam("sortBy") String sortBy, @RequestParam("limit") Integer limit, @RequestParam("offset") Integer offset) {
        Sort.Direction direction = "create".equals(sortBy) ? Sort.Direction.ASC : Sort.Direction.DESC;
        PageRequest pageRequest = PageRequest.of(offset, limit, Sort.by(direction, "createdAt"));
        return userService.getMyComment(userId, pageRequest);
    }
}
