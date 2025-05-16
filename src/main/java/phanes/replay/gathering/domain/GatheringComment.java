package phanes.replay.gathering.domain;

import jakarta.persistence.*;
import lombok.*;
import phanes.replay.user.domain.User;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GatheringComment extends BaseTimeEntity {

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

    public void updateComment(String content) {
        this.content = content;
    }
}
