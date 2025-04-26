package phanes.replay.review.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import phanes.replay.roomescape.domain.RoomEscape;
import phanes.replay.user.domain.User;

import java.math.BigDecimal;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomescape_id")
    private RoomEscape roomEscape;

    @Column
    private BigDecimal score; // 리뷰 점수

    @Column
    private String content; // 리뷰 본문

    @Column
    private Boolean success; // 성공 여부

    @Column
    private Integer hint; // 힌트 사용수

    @Column(length = 5)
    private String theme_review; // 테마 리뷰

    @Column(length = 5)
    private String level_review; // 난이도 리뷰

    @Column(length = 5)
    private String story_review; // 스토리 리뷰

}
