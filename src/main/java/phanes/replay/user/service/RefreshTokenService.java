package phanes.replay.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import phanes.replay.config.properties.JwtProperties;
import phanes.replay.exception.TokenExpiredException;
import phanes.replay.exception.UnAuthenticateException;
import phanes.replay.security.JwtProvider;
import phanes.replay.user.domain.RefreshToken;
import phanes.replay.user.domain.User;
import phanes.replay.user.repository.RefreshTokenRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final UserQueryService userQueryService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RedisTemplate<String, Long> redisTemplate;
    private final JwtProperties jwtProperties;
    private final JwtProvider jwtProvider;

    @Transactional
    public String createRefreshToken(Long userId) {
        User user = userQueryService.findById(userId);
        String token = UUID.randomUUID().toString();
        Instant expireTime = Instant.now().plusMillis(jwtProperties.getRefreshToken().getExpireTime().toMillis());
        Optional<RefreshToken> userRefreshToken = refreshTokenRepository.findByUserId(userId);
        RefreshToken refreshToken = userRefreshToken.isPresent() ? userRefreshToken.get().updateToken(token, expireTime) : RefreshToken.builder()
                .token(token)
                .user(user)
                .expireDate(expireTime)
                .build();
        refreshTokenRepository.save(refreshToken);
        return token;
    }

    @Transactional
    public String getAccessTokenIfRefreshTokenIsValid(String refreshToken) {
        RefreshToken existedToken = refreshTokenRepository.findByToken(refreshToken).orElseThrow(() -> new UnAuthenticateException("Invalid refresh token"));
        if (existedToken.getExpireDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(existedToken);
            throw new TokenExpiredException("Refresh token expired");
        }
        String accessToken = jwtProvider.generateAccessToken(existedToken.getId(), existedToken.getUser().getRole());
        redisTemplate.opsForValue().set(accessToken, existedToken.getUser().getId());
        return accessToken;
    }

    @Transactional
    public void logout(String accessToken) {
        Long userId = redisTemplate.opsForValue().get(accessToken);
        RefreshToken refreshToken = refreshTokenRepository.findByUserId(userId).orElseThrow(() -> new IllegalStateException("Invalid refresh token"));
        refreshTokenRepository.delete(refreshToken);
        redisTemplate.delete(accessToken);
    }
}