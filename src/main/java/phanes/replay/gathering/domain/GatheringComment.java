package phanes.replay.gathering.domain;

import jakarta.persistence.*;
import lombok.Getter;
import phanes.replay.user.domain.User;

import java.time.LocalDateTime;

@Entity
@Getter
public class GatheringComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Gathering gathering;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column
    private String content; // 댓글내용

    @Column
    private Long parentId; // 부모 댓글

    @Column
    private LocalDateTime createdAt; // 생성일

    @Column
    private LocalDateTime updatedAt; // 수정일
}
