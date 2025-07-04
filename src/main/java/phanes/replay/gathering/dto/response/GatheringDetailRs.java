package phanes.replay.gathering.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GatheringDetailRs {

    private Long gatheringId;
    private String name;
    private String detailImage;
    private Integer capacity;
    private LocalDateTime dateTime;
    private Long price;
    private Boolean isIndividual;
    private String content;
    private LocalDateTime registrationStart;
    private LocalDateTime registrationEnd;
}