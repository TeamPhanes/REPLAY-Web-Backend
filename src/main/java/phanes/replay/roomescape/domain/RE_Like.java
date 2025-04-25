package phanes.replay.roomescape.domain;

import jakarta.persistence.*;
import lombok.Getter;
import phanes.replay.user.domain.User;

@Getter
@Entity
@Table(name = "roomescape_like")
public class RE_Like {

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
