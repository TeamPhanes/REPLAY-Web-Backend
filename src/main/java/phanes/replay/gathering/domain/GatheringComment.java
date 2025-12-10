package phanes.replay.gathering.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import phanes.replay.common.domain.BaseTimeEntity;
import phanes.replay.user.domain.User;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GatheringComment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Gathering gathering;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @Column(nullable = false)
    private String content;
    private Long parentId;
}