package phanes.replay.user.controller.request;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UpdateMeRequest {

    private MultipartFile image;
    private String nickname;
    private String comment;
    @Pattern(regexp = "true|false")
    private String emailMark;
    @Pattern(regexp = "true|false")
    private String genderMark;
}
