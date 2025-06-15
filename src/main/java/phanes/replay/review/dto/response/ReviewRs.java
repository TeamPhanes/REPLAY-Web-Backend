package phanes.replay.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRs {

    private String content;
    private List<String> images;
    private Double rating;
    private Boolean success;
    private Integer hint;
    private Integer playUser;
    private String themeReview;
    private String levelReview;
    private String storyReview;
    private ReviewUserDTO user;
    private LocalDateTime createdAt;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewUserDTO {
        private String name;
        private String image;
    }
}
