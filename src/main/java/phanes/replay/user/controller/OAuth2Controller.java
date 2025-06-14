package phanes.replay.user.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;
import phanes.replay.annotation.ValidateSocialType;
import phanes.replay.config.properties.DomainProperties;
import phanes.replay.exception.StateNotFoundException;
import phanes.replay.user.domain.enums.SocialType;
import phanes.replay.user.dto.oauth.SecurityToken;
import phanes.replay.user.service.OAuth2Service;
import phanes.replay.user.service.RefreshTokenService;
import phanes.replay.utils.CookieUtils;

import java.io.IOException;
import java.time.Duration;
import java.util.Locale;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class OAuth2Controller {

    private final OAuth2Service oauth2Service;
    private final RefreshTokenService refreshTokenService;
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
    @GetMapping(value = "/{socialType}/callback", produces = MediaType.TEXT_HTML_VALUE)
    public String socialLoginCallback(@PathVariable("socialType") String socialType, @RequestParam("code") String code, @RequestParam("state") String state, HttpServletResponse response) throws IOException {
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
        return String.format("""
                <html>
                <body>
                <script>
                  window.opener.postMessage({
                    type: 'token',
                    accessToken: '%s',
                  }, '%s');
                  window.close();
                </script>
                </body>
                </html>
                """, token.getAccessToken(), domainProperties.getFrontend());
    }

    @PostMapping("/refresh")
    public void refresh(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = CookieUtils.getCookieValue(request, "refreshToken");
        String newAccessToken = refreshTokenService.getAccessTokenIfRefreshTokenIsValid(refreshToken);
        response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + newAccessToken);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION).replace("Bearer ", "");
        refreshTokenService.logout(accessToken);
        redisTemplate.delete(accessToken);

        ResponseCookie deleteCookie = CookieUtils.deleteCookie("refreshToken", domainProperties.getCookie());
        response.addHeader(HttpHeaders.SET_COOKIE, deleteCookie.toString());
    }
}