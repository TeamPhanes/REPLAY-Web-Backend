package phanes.replay.user.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import phanes.replay.config.properties.GoogleProperties;
import phanes.replay.user.client.GoogleProfileClient;
import phanes.replay.user.client.GoogleTokenClient;
import phanes.replay.user.domain.enums.SocialType;
import phanes.replay.user.dto.oauth.GoogleProfileResponse;
import phanes.replay.user.dto.oauth.GoogleTokenResponse;
import phanes.replay.user.dto.oauth.SocialProfile;

@Component
@RequiredArgsConstructor
public class GoogleLoginStrategy implements SocialLoginStrategy {

    private final GoogleTokenClient googleTokenClient;
    private final GoogleProfileClient googleProfileClient;
    private final GoogleProperties properties;

    @Override
    public String getSocialUrl(String state) {
        return UriComponentsBuilder
                .fromUriString("https://accounts.google.com/o/oauth2/v2/auth")
                .queryParam("response_type", "code")
                .queryParam("client_id", properties.getClientId())
                .queryParam("redirect_uri", properties.getRedirectUri())
                .queryParam("scope", "email profile")
                .queryParam("access_type", "offline")
                .queryParam("prompt", "consent")
                .queryParam("state", state)
                .build()
                .toUriString();
    }

    @Override
    public SocialType getSocialType() {
        return SocialType.GOOGLE;
    }

    @Override
    public SocialProfile getProfile(String code, String state) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code");
        formData.add("client_id", properties.getClientId());
        formData.add("client_secret", properties.getClientSecret());
        formData.add("redirect_uri", properties.getRedirectUri());
        formData.add("code", code);

        GoogleTokenResponse tokenResponse = googleTokenClient.getToken(formData);
        GoogleProfileResponse profile = googleProfileClient.getProfile("Bearer " + tokenResponse.getAccessToken());
        return SocialProfile.builder()
                .socialId(profile.getId())
                .profileImage(profile.getPicture())
                .socialType(SocialType.GOOGLE)
                .email(profile.getEmail())
                .build();
    }
}
