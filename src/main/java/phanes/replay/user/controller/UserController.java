package phanes.replay.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import phanes.replay.user.dto.UserDTO;
import phanes.replay.user.service.UserService;

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
    public void patch(@AuthenticationPrincipal Long userId, @RequestPart MultipartFile image, @RequestPart String nickname, @RequestPart String comment) {
        userService.updateUser(userId, image, nickname, comment);
    }
}
