package phanes.replay.gathering.service;

import org.springframework.stereotype.Component;
import phanes.replay.gathering.controller.GatheringCreateRequest;
import phanes.replay.gathering.domain.Gathering;
import phanes.replay.gathering.domain.Gathering_Content;
import phanes.replay.roomescape.domain.RoomEscape;

@Component
public class GatheringMapper {

    public Gathering requestToEntity(GatheringCreateRequest request, RoomEscape roomEscape) {
        return Gathering.create(
                roomEscape,
                request.getName(),
                request.getDatetime(),
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
