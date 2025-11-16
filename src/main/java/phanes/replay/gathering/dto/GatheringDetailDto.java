package phanes.replay.gathering.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GatheringDetailDto {

    private Long id;
    private Long themeId;
    private String name;
    private Integer capacity;
    private LocalDateTime date;
    private LocalDateTime registrationStart;
    private LocalDateTime registrationEnd;
    private String content;
    private String image;
    private Long price;
    private Boolean isIndividual;
    private String title;
    private Boolean isLiked;
}