package phanes.replay.gathering.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import phanes.replay.user.domain.User;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GatheringMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Gathering gathering;

    @Enumerated(EnumType.STRING)
    private Role role;

    public static GatheringMember createHost(User host, Gathering gathering) {
        GatheringMember member = new GatheringMember();
        member.user = host;
        member.gathering = gathering;
        member.role = Role.HOST;
        return member;
    }
    public static GatheringMember createMember(User user, Gathering gathering) {
        GatheringMember member = new GatheringMember();
        member.user = user;
        member.gathering = gathering;
        member.role = Role.MEMBER;
        return member;
    }
}
