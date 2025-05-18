package phanes.replay.gathering.service;

import org.springframework.stereotype.Component;
import phanes.replay.gathering.domain.Gathering;
import phanes.replay.gathering.domain.GatheringContent;
import phanes.replay.gathering.dto.request.GatheringCreateRq;
import phanes.replay.theme.domain.Theme;

@Component
public class GatheringMapper {

    public Gathering requestToEntity(GatheringCreateRq request, Theme theme) {
        return Gathering.create(
                theme,
                request.getName(),
                request.getDateTime(),
                request.getRegistrationStart(),
                request.getRegistrationEnd(),
                request.getCapacity()
        );
    }

    public GatheringContent ToContentEntity(GatheringCreateRq request, Gathering gathering) {
        return GatheringContent.create(
                gathering,
                request.getContent(),
                request.getPrice()
        );
    }
}
