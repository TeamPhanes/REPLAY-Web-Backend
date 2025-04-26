package phanes.replay.gathering.domain;

import jakarta.persistence.*;
import lombok.Getter;
import phanes.replay.user.domain.User;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "gathering_comment")
public class Gathering_Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gatherings_id")
    private Gathering gathering;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String content; // 댓글내용

    @Column
    private Long parent_id; // 부모 댓글

    @Column
    private LocalDateTime created_at; // 생성일

    @Column
    private LocalDateTime updated_at; // 수정일
}
