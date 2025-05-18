package phanes.replay.gathering.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GatheringRq {

    private String sortBy;
    private String keyword;
    private String location;
    private String date;
    private String genre;
    private Integer limit;
    private Integer offset;
}
