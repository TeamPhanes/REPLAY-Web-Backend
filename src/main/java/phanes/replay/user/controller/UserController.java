package phanes.replay.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import phanes.replay.annotation.ValidateSocialType;
import phanes.replay.config.properties.DomainProperties;
import phanes.replay.exception.StateNotFoundException;
import phanes.replay.user.domain.enums.SocialType;
import phanes.replay.user.dto.SecurityToken;
import phanes.replay.user.dto.UserDTO;
import phanes.replay.user.service.OAuth2Service;
import phanes.replay.user.service.RefreshTokenService;
import phanes.replay.user.service.UserService;
import phanes.replay.utils.CookieUtils;

import java.io.IOException;
import java.time.Duration;
import java.util.Locale;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final OAuth2Service oauth2Service;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;
    private final RedisTemplate<String, Boolean> redisTemplate;
    private final DomainProperties domainProperties;

    @ValidateSocialType
    @GetMapping("/{socialType}")
    public void getSocialLoginProviderUrl(@PathVariable("socialType") String socialType, HttpServletResponse response) throws IOException {
        String state = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(state, true, Duration.ofMinutes(5));
        SocialType socialTypeEnum = SocialType.valueOf(socialType.toUpperCase(Locale.KOREA));

        String authUrl = oauth2Service.getProviderUrl(socialTypeEnum, state);
        response.sendRedirect(authUrl);
    }

    @ValidateSocialType
    @GetMapping("/{socialType}/callback")
    public void socialLoginCallback(@PathVariable("socialType") String socialType, @RequestParam("code") String code, @RequestParam("state") String state, HttpServletResponse response) throws IOException {
        Boolean isValid = redisTemplate.opsForValue().getAndDelete(state);
        if (Boolean.FALSE.equals(isValid)) {
            throw new StateNotFoundException("state not found");
        }
        redisTemplate.delete(state);

        SocialType socialTypeEnum = SocialType.valueOf(socialType.toUpperCase(Locale.KOREA));
        SecurityToken token = oauth2Service.login(code, state, socialTypeEnum);

        ResponseCookie cookie = CookieUtils.createHttpOnlyCookie("refreshToken", token.getRefreshToken(), Duration.ofDays(7), domainProperties.getCookie());
        response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token.getAccessToken());
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        response.sendRedirect(domainProperties.getFrontend());
    }

    @PostMapping("/refresh")
    public void refresh(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = CookieUtils.getCookieValue(request, "refreshToken");
        String newAccessToken = refreshTokenService.getAccessTokenIfRefreshTokenIsValid(refreshToken);
        response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + newAccessToken);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION).replace("Bearer ", "");
        refreshTokenService.logout(accessToken);
        redisTemplate.delete(accessToken);

        ResponseCookie deleteCookie = CookieUtils.deleteCookie("refreshToken", domainProperties.getCookie());
        response.addHeader(HttpHeaders.SET_COOKIE, deleteCookie.toString());
    }

    @GetMapping("/me")
    public UserDTO me(@AuthenticationPrincipal Long userId) {
        return userService.getUser(userId);
    }
}