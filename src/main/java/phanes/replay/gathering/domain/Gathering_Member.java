package phanes.replay.gathering.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import phanes.replay.user.domain.User;

@Entity
@Getter
@NoArgsConstructor
public class Gathering_Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Gathering gathering;

    @Enumerated(EnumType.STRING)
    private Role role;

    public static Gathering_Member createHost(User host, Gathering gathering) {
        Gathering_Member member = new Gathering_Member();
        member.user = host;
        member.gathering = gathering;
        member.role = Role.HOST;
        return member;
    }
    public static Gathering_Member createMember(User user, Gathering gathering) {
        Gathering_Member member = new Gathering_Member();
        member.user = user;
        member.gathering = gathering;
        member.role = Role.MEMBER;
        return member;
    }
}
