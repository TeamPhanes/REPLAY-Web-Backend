package phanes.replay.gathering.domain;

import jakarta.persistence.*;
import lombok.*;
import phanes.replay.roomescape.domain.RoomEscape;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "gatherings")
public class Gathering extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomescape_id")
    private RoomEscape roomEscape;

    @Column(nullable = false, length = 20)
    private String name; // 모임 제목

    @Column(nullable = false)
    private LocalDateTime dateTime; // 모임 일정

    @Column(nullable = false)
    private LocalDateTime registrationStart; // 모집 날짜

    @Column(nullable = false)
    private LocalDateTime registrationEnd; // 마감 날짜

    @Column(nullable = false)
    private Integer capacity; // 모집 인원

    @Column
    private String listImage; // 목록 이미지

    public static Gathering create(
            RoomEscape roomEscape,
            String name,
            LocalDateTime dateTime,
            LocalDateTime registrationStart,
            LocalDateTime registrationEnd,
            Integer capacity){
        Gathering gathering = new Gathering();
        gathering.roomEscape = roomEscape;
        gathering.name = name;
        gathering.dateTime = dateTime;
        gathering.registrationStart = registrationStart;
        gathering.registrationEnd = registrationEnd;
        gathering.capacity = capacity;
        return gathering;
    }

    @OneToMany(mappedBy = "gathering")
    private List<Gathering_Member> gathering_member = new ArrayList<>();

}
