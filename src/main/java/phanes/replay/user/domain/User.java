package phanes.replay.user.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10)
    private String nickname; // 닉네임

    @Column(length = 2)
    private String gender; // 성별

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String profile; // 프로필 소개

    @Column
    private String profile_image; // 프로필 이미지

    @Enumerated(EnumType.STRING)
    private Sns_Type sns_type; // sns 타입

    @Column
    private Boolean gender_mark; // 성별 표시여부
    @Column
    private Boolean email_mark; // 이메일 표시여부
}
