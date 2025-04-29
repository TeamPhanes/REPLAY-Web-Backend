package phanes.replay.roomescape.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roomEscape")
public class RoomEscape {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String name; // 테마 이름
    @Column(length = 100)
    private String address; // 상세 주소
    @Column
    private Integer playtime; // 플레이 시간

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'NORMAL'") // default
    private Level level; // 난이도

    @Column(length = 15)
    private String cafe; // 방탈출 카페
    @Column(length = 15)
    private String spot; // 지점명
    @Column(length = 10)
    private String state; // 시도
    @Column(length = 10)
    private String city; // 시구군
    @Column
    private String image;

    @OneToMany(mappedBy = "roomEscape")
    private List<Genres> genres = new ArrayList<>();
}
