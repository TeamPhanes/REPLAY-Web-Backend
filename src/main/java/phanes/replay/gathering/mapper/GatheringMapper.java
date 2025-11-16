package phanes.replay.gathering.mapper;

import org.mapstruct.Mapper;
import phanes.replay.gathering.dto.GatheringDto;
import phanes.replay.gathering.dto.response.GatheringRs;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GatheringMapper {

    GatheringRs toGatheringRs(GatheringDto gatheringDto, List<String> genres);
}