package phanes.replay.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import phanes.replay.user.dto.UserDTO;
import phanes.replay.user.service.UserService;

import java.util.List;

@RestController("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public UserDTO me(HttpServletRequest request) {
        String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION).replace("Bearer ", "");
        return userService.getUser(accessToken);
    }

    @PatchMapping(name = "/me", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateMe(HttpServletRequest request, @RequestPart("image") MultipartFile image, @RequestPart("nickname") String nickname, @RequestPart("comment") String comment, @RequestPart("representAchievement") List<String> representAchievement) {
        String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION).replace("Bearer ", "");
        userService.UpdateUser(accessToken, image, nickname, comment);
    }
}