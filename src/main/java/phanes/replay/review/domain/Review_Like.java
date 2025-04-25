package phanes.replay.review.domain;

import jakarta.persistence.*;
import lombok.Getter;
import phanes.replay.roomescape.domain.RoomEscape;
import phanes.replay.user.domain.User;

@Entity
@Getter
@Table(name = "review_like")
public class Review_Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomescape_id")
    private RoomEscape roomEscape;

    @Column
    private Long like;
}
