package phanes.replay.user.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import phanes.replay.user.dto.UserResponse;

@FeignClient(name = "userClient", url = "https://replay-oauth.phanescloud.com")
public interface UserClient {

    @GetMapping("/auth/me")
    UserResponse getUserInfo(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization);

    @PatchMapping("/auth/me")
    void patchUserInfo(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,String image, String nickname, String comment);
}