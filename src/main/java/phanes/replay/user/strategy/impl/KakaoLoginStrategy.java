package phanes.replay.user.strategy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import phanes.replay.config.properties.KakaoProperties;
import phanes.replay.user.client.KakaoProfileClient;
import phanes.replay.user.client.KakaoTokenClient;
import phanes.replay.user.domain.enums.SocialType;
import phanes.replay.user.dto.oauth.SocialProfile;
import phanes.replay.user.dto.oauth.kakao.KakaoProfileResponse;
import phanes.replay.user.dto.oauth.kakao.KakaoTokenResponse;
import phanes.replay.user.dto.oauth.kakao.KakaoUserInfo;
import phanes.replay.user.strategy.SocialLoginStrategy;

@Component
@RequiredArgsConstructor
public class KakaoLoginStrategy implements SocialLoginStrategy {

    private final KakaoTokenClient kakaoTokenClient;
    private final KakaoProfileClient kakaoProfileClient;
    private final KakaoProperties properties;

    @Override
    public String getSocialUrl(String state) {
        return UriComponentsBuilder
                .fromUriString("https://kauth.kakao.com/oauth/authorize")
                .queryParam("response_type", "code")
                .queryParam("client_id", properties.getClientId())
                .queryParam("redirect_uri", properties.getRedirectUri())
                .queryParam("state", state)
                .build()
                .toUriString();
    }

    @Override
    public SocialType getSocialType() {
        return SocialType.KAKAO;
    }

    @Override
    public SocialProfile getProfile(String code, String state) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code");
        formData.add("client_id", properties.getClientId());
        formData.add("client_secret", properties.getClientSecret());
        formData.add("redirect_uri", properties.getRedirectUri());
        formData.add("code", code);

        KakaoTokenResponse tokenResponse = kakaoTokenClient.getToken(formData);
        KakaoProfileResponse profileResponse = kakaoProfileClient.getProfile("Bearer " + tokenResponse.getAccessToken());
        KakaoUserInfo profile = profileResponse.getKakao_account().getProfile();
        return SocialProfile.builder()
                .socialId(String.valueOf(profileResponse.getId()))
                .profileImage(profile.getThumbnailImageUrl())
                .socialType(SocialType.KAKAO)
                .email(profileResponse.getKakao_account().getEmail())
                .build();
    }
}