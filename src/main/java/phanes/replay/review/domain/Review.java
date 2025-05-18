package phanes.replay.review.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import phanes.replay.common.domain.BaseTimeEntity;
import phanes.replay.theme.domain.Theme;
import phanes.replay.user.domain.User;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private Boolean success;
    private Double score;
    private Integer hint;
    private Integer numberOfPlayer;
    private String themeReview;
    private String levelReview;
    private String storyReview;
    @ManyToOne
    private User user;
    @ManyToOne
    private Theme theme;
    @OneToMany(mappedBy = "review")
    private List<ReviewImage> images;

    public void updateReview(Double myRating, Integer hint, Integer numberOfPlayer, String themeReview, String levelReview, String storyReview, String reviewComment, Boolean success) {
        this.score = myRating;
        this.hint = hint;
        this.numberOfPlayer = numberOfPlayer;
        this.themeReview = themeReview;
        this.levelReview = levelReview;
        this.storyReview = storyReview;
        this.success = success;
        this.content = reviewComment;
    }
}
