package phanes.replay.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import phanes.replay.review.domain.enums.Eval;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRs {

    private Long id;
    private String nickname;
    private String profileImage;
    private Double score;
    private String content;
    private List<String> images;
    private Boolean isSuccess;
    private Integer hint;
    private Integer numberOfPlayer;
    private Eval themeReview;
    private Eval levelReview;
    private Eval storyReview;
    private Long likeCount;
    private Boolean isLiked;
    private LocalDateTime createdAt;
}