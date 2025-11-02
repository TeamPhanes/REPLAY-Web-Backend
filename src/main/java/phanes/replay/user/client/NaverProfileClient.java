package phanes.replay.user.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import phanes.replay.user.dto.oauth.naver.NaverProfileResponse;

@FeignClient(value = "Naver", url = "https://openapi.naver.com")
public interface NaverProfileClient {
    @GetMapping("/v1/nid/me")
    NaverProfileResponse getProfile(@RequestHeader("Authorization") String token);
}