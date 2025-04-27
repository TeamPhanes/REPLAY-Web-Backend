package phanes.replay.user.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import phanes.replay.user.domain.enums.SocialType;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nickname;
    private String profileComment;
    private String profileImage;
    private String gender;
    private String email;
    private String socialId;
    @Enumerated(EnumType.STRING)
    private SocialType socialType;
    private Boolean genderMark;
    private Boolean emailMark;
}
