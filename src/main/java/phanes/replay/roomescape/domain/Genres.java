package phanes.replay.roomescape.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "re_genre")
public class Genres {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 8)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomEscape_id")
    private RoomEscape roomEscape;


}
