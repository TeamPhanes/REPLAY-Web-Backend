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
    @Column(length = 50, nullable = false)
    private String email;
    @Column(length = 200)
    private String profileComment;
    private String profileImage;
    @Column(length = 100)
    private String socialId;
    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private SocialType socialType;
    @Column(nullable = false)
    private Boolean genderMark;
    @Column(nullable = false)
    private Boolean emailMark;
}