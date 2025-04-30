package phanes.replay.user.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import phanes.replay.gathering.domain.BaseTimeEntity;
import phanes.replay.user.domain.enums.SocialType;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseTimeEntity {

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

    public void updateUserInfo(String profileImage, String nickname, String profileComment, Boolean emailMark, Boolean genderMark) {
        this.profileImage = profileImage;
        this.nickname = nickname;
        this.profileComment = profileComment;
        this.emailMark = emailMark;
        this.genderMark = genderMark;
    }
}
