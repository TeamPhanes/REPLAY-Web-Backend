package phanes.replay.user.controller.request;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class UpdateMeRequest {

    private MultipartFile image;
    private String nickname;
    private String comment;
    @Pattern(regexp = "true|false")
    private String emailMark;
    @Pattern(regexp = "true|false")
    private String genderMark;
}
