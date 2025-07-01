package phanes.replay.user.dto.user.request;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UserUpdateRq {

    private MultipartFile image;
    private String nickname;
    private String comment;
    @Pattern(regexp = "true|false")
    private String emailMark;
    @Pattern(regexp = "true|false")
    private String genderMark;
}