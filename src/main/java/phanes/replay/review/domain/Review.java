package phanes.replay.review.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import phanes.replay.common.domain.BaseTimeEntity;
import phanes.replay.review.dto.request.ReviewUpdateRq;
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

    public void updateReview(ReviewUpdateRq reviewUpdateRq) {
        this.score = reviewUpdateRq.getRating();
        this.hint = reviewUpdateRq.getHint();
        this.numberOfPlayer = reviewUpdateRq.getNumberOfPlayer();
        this.themeReview = reviewUpdateRq.getThemeReview();
        this.levelReview = reviewUpdateRq.getLevelReview();
        this.storyReview = reviewUpdateRq.getStoryReview();
        this.success = reviewUpdateRq.getSuccess();
        this.content = reviewUpdateRq.getContent();
    }
}
