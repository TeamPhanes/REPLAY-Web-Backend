package phanes.replay.user.service;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import phanes.replay.security.JwtProvider;
import phanes.replay.user.domain.User;
import phanes.replay.user.domain.enums.SocialType;
import phanes.replay.user.dto.oauth.SecurityToken;
import phanes.replay.user.dto.oauth.SocialProfile;
import phanes.replay.user.persistence.repository.UserRepository;
import phanes.replay.user.strategy.SocialLoginStrategy;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OAuth2Service {

    private final List<SocialLoginStrategy> strategies;
    private Map<SocialType, SocialLoginStrategy> strategyMap;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;
    private final RedisTemplate<String, Long> redisTemplate;

    @PostConstruct
    public void init() {
        this.strategyMap = strategies.stream().collect(Collectors.toMap(SocialLoginStrategy::getSocialType, Function.identity()));
    }

    public String getProviderUrl(SocialType socialType, String state) {
        SocialLoginStrategy strategy = strategyMap.get(socialType);
        return strategy.getSocialUrl(state);
    }

    public SecurityToken login(String code, String state, SocialType socialType) {
        SocialLoginStrategy strategy = strategyMap.get(socialType);
        SocialProfile profile = strategy.getProfile(code, state);
        Optional<User> savedUser = userRepository.findBySocialIdAndSocialType(profile.getSocialId(), socialType);
        User user = savedUser.orElseGet(() -> userRepository.save(User.builder()
                .nickname(NanoIdUtils.randomNanoId())
                .socialId(profile.getSocialId())
                .profileImage(profile.getProfileImage())
                .socialType(socialType)
                .email(profile.getEmail())
                .genderMark(false)
                .emailMark(false)
                .profileComment("")
                .build()));
        String refreshToken = refreshTokenService.createRefreshToken(user.getId());
        String accessToken = jwtProvider.generateAccessToken(user.getId());
        redisTemplate.opsForValue().set(accessToken, user.getId());
        return SecurityToken.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}