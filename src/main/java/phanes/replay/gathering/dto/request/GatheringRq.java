package phanes.replay.gathering.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GatheringRq {

    private Long themeId;
    private String name;
    private Integer capacity;
    private LocalDateTime date;
    private LocalDateTime registrationStart;
    private LocalDateTime registrationEnd;
    private String content;
    private Boolean isIndividual;
    private Long price;
}