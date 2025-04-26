package phanes.replay.roomescape.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "roomescape_content")
public class RE_Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomescape_id")
    private RoomEscape roomEscape;

    @Column
    private String story;

    @Column
    private String image; // 상세페이지용 이미지
}
