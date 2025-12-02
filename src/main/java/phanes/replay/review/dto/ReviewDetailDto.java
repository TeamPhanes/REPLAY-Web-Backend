package phanes.replay.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import phanes.replay.review.domain.enums.Eval;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDetailDto {

    private Long id;
    private String nickname;
    private String profileImage;
    private Double score;
    private String content;
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