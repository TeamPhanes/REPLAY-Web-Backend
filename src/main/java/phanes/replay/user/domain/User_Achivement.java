package phanes.replay.user.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "user_achivement")
public class User_Achivement {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "achivement")
    private Achivement achivement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private Integer progress; // 진행률

    @Column
    private Boolean completed; // 달성 여부

    @Column
    private LocalDateTime completed_at; // 달성일
}
