package phanes.replay.user.strategy;

import phanes.replay.user.domain.enums.SocialType;
import phanes.replay.user.dto.oauth.SocialProfile;

public interface SocialLoginStrategy {
    String getSocialUrl(String state);
    SocialType getSocialType();
    SocialProfile getProfile(String code, String state);
}
