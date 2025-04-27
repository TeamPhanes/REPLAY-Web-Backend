package phanes.replay.review.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import phanes.replay.roomescape.domain.RoomEscape;
import phanes.replay.user.domain.User;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private Boolean success;
    private Integer score;
    private Integer hint;
    private String themeReview;
    private String levelReview;
    private String storyReview;
    @ManyToOne
    private User user;
    @ManyToOne
    private RoomEscape roomEscape;
}
