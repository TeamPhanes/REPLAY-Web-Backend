package phanes.replay.user.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import phanes.replay.common.domain.BaseTimeEntity;
import phanes.replay.user.domain.enums.SocialType;

@Entity
@Getter
@Builder
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 32, unique = true, nullable = false)
    private String nickname;
    @Column(length = 2)
    private String gender;
    @Column(length = 50)
    private String email;
    @Column(length = 200)
    private String profileComment;
    private String profileImage;
    @Column(length = 100)
    private String socialId;
    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private SocialType socialType;
    private Boolean genderMark;
    private Boolean emailMark;
}