package phanes.replay.user.domain;

import jakarta.persistence.*;
import lombok.Getter;
import phanes.replay.roomescape.domain.RoomEscape;

@Entity
@Getter
@Table(name = "roomescape_participate")
public class RE_Participate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomescape_id")
    private RoomEscape roomEscape;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
