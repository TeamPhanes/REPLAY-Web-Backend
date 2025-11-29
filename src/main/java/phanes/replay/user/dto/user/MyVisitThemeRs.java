package phanes.replay.user.dto.user;

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
public class MyVisitThemeRs {

    private Long id;
    private String title;
    private String image;
    private List<String> genres;
    private LocalDateTime visitDate;
    private Double score;
    private Eval themeReview;
    private Eval levelReview;
    private Eval storyReview;
    private Integer hint;
    private Integer numberOfPlayer;
    private Boolean isSuccess;
    private String content;
    private String reviewImage;
}