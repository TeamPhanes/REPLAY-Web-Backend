package phanes.replay.gathering.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GatheringDetailRs {

    private Long gatheringId;
    private String detailImage;
    private Long price;
    private Boolean isIndividual;
    private String content;
}
