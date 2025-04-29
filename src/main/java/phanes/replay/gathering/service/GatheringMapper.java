package phanes.replay.gathering.service;

import org.springframework.stereotype.Component;
import phanes.replay.gathering.controller.GatheringCreateRequest;
import phanes.replay.gathering.domain.Gathering;
import phanes.replay.gathering.domain.Gathering_Content;
import phanes.replay.theme.domain.Theme;

@Component
public class GatheringMapper {

    public Gathering requestToEntity(GatheringCreateRequest request, Theme theme) {
        return Gathering.create(
                theme,
                request.getName(),
                request.getDateTime(),
                request.getRegistrationStart(),
                request.getRegistrationEnd(),
                request.getCapacity()
        );
    }

    public Gathering_Content ToContentEntity(GatheringCreateRequest request, Gathering gathering) {
        return Gathering_Content.create(
                gathering,
                request.getContent(),
                request.getPrice()
        );
    }
}
