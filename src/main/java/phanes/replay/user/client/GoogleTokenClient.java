package phanes.replay.user.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import phanes.replay.user.dto.GoogleTokenResponse;

@FeignClient(name = "googleTokenClient", url = "https://oauth2.googleapis.com")
public interface GoogleTokenClient {

    @PostMapping(value = "/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    GoogleTokenResponse getToken(@RequestBody MultiValueMap<String, String> form);
}