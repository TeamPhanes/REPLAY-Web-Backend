package phanes.replay.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import phanes.replay.user.dto.OtherUserDTO;
import phanes.replay.user.dto.UserDTO;
import phanes.replay.user.dto.UserParticipatingGatheringDTO;
import phanes.replay.user.dto.UserPlayThemeDTO;
import phanes.replay.user.service.UserService;

import java.util.List;

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
    public void updateMe(@AuthenticationPrincipal Long userId, @RequestPart MultipartFile image, @RequestPart String nickname, @RequestPart String comment, @RequestPart Boolean emailMark, @RequestPart Boolean genderMark) {
        userService.updateUser(userId, image, nickname, comment, emailMark, genderMark);
    }

    @GetMapping("/me/theme")
    public List<UserPlayThemeDTO> myPlayTheme(@AuthenticationPrincipal Long userId) {
        return userService.getMyPlayingTheme(userId);
    }

    @PatchMapping("/me/theme")
    public void updateMyPlayTheme(@AuthenticationPrincipal Long userId, @RequestBody UserPlayThemeDTO theme) {
        userService.updateThemeReview(userId, theme);
    }

    @GetMapping("/{nickname}")
    public OtherUserDTO getOtherUser(@PathVariable String nickname) {
        return userService.getUserByNickname(nickname);
    }

    @GetMapping("/me/gathering")
    public List<UserParticipatingGatheringDTO> myParticipatingGathering(@AuthenticationPrincipal Long userId, @RequestParam("limit") Integer limit, @RequestParam("offset") Integer offset) {
        PageRequest pageRequest = PageRequest.of(offset, limit);
        return userService.getMyParticipatingGathering(userId, pageRequest);
    }
}
