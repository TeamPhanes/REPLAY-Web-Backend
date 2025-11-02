package phanes.replay.user.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import phanes.replay.config.properties.NaverProperties;
import phanes.replay.user.client.NaverProfileClient;
import phanes.replay.user.client.NaverTokenClient;
import phanes.replay.user.domain.enums.SocialType;
import phanes.replay.user.dto.oauth.SocialProfile;
import phanes.replay.user.dto.oauth.naver.NaverProfile;
import phanes.replay.user.dto.oauth.naver.NaverProfileResponse;
import phanes.replay.user.dto.oauth.naver.NaverTokenResponse;
import phanes.replay.user.strategy.SocialLoginStrategy;

@Component
@RequiredArgsConstructor
public class NaverLoginStrategy implements SocialLoginStrategy {

    private final NaverTokenClient naverTokenClient;
    private final NaverProfileClient naverProfileClient;
    private final NaverProperties properties;

    @Override
    public String getSocialUrl(String state) {
        return UriComponentsBuilder
                .fromUriString("https://nid.naver.com/oauth2.0/authorize")
                .queryParam("response_type", "code")
                .queryParam("client_id", properties.getClientId())
                .queryParam("redirect_uri", properties.getRedirectUri())
                .queryParam("state", state)
                .build()
                .toUriString();
    }

    @Override
    public SocialType getSocialType() {
        return SocialType.NAVER;
    }

    @Override
    public SocialProfile getProfile(String code, String state) {
        NaverTokenResponse tokenResponse = naverTokenClient.getToken("authorization_code", properties.getClientId(), properties.getClientSecret(), code, state);
        NaverProfileResponse profileResponse = naverProfileClient.getProfile("Bearer " + tokenResponse.getAccessToken());
        NaverProfile profile = profileResponse.getResponse();
        return SocialProfile.builder()
                .socialId(profile.getId())
                .profileImage(profile.getProfileImage())
                .socialType(SocialType.NAVER)
                .email(profile.getEmail())
                .build();
    }
}