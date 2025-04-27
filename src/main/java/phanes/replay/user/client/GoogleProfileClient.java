package phanes.replay.user.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import phanes.replay.user.dto.GoogleProfileResponse;

@FeignClient(name = "googleProfileClient", url = "https://www.googleapis.com")
public interface GoogleProfileClient {

    @GetMapping("/oauth2/v2/userinfo")
    GoogleProfileResponse getProfile(@RequestHeader("Authorization") String authorization);
}