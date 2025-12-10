package phanes.replay.review.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import phanes.replay.common.domain.CreateTimeEntity;
import phanes.replay.review.domain.enums.Eval;
import phanes.replay.theme.domain.ThemeVisit;
import phanes.replay.user.domain.User;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review extends CreateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private ThemeVisit themeVisit;
    @ManyToOne
    private User user;
    @Column(nullable = false)
    private Double score;
    @Column(length = 200, nullable = false)
    private String content;
    @Column(nullable = false)
    private Boolean isSuccess;
    @Column(nullable = false)
    private Integer hint;
    @Column(nullable = false)
    private Integer numberOfPlayer;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Eval themeReview;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Eval levelReview;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Eval storyReview;
}