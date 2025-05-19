package phanes.replay.gathering.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import phanes.replay.gathering.domain.GatheringContent;
import phanes.replay.gathering.dto.query.GatheringQuery;
import phanes.replay.gathering.dto.response.GatheringDetailRs;
import phanes.replay.gathering.dto.response.GatheringRs;

@Mapper(componentModel = "spring")
public interface GatheringMapper {

    @Mapping(target = "genres", expression = "java(java.util.Arrays.asList(gatheringQuery.getGenres().split(\",\")))")
    GatheringRs toGatheringRs(GatheringQuery gatheringQuery);

    @Mapping(target = "gatheringId", source = "gathering.id")
    @Mapping(target = "detailImage", source = "image")
    GatheringDetailRs toGatheringDetailRs(GatheringContent byGatheringId);
}
