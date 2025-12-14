package phanes.replay.gathering.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import phanes.replay.gathering.dto.GatheringCommentDto;
import phanes.replay.gathering.dto.GatheringDetailDto;
import phanes.replay.gathering.dto.GatheringDto;
import phanes.replay.gathering.dto.response.*;
import phanes.replay.opensearch.domain.GatheringDoc;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GatheringMapper {

    GatheringRs toGatheringRs(GatheringDto gatheringDto, List<String> genres);

    GatheringDetailRs toGatheringDetailRs(GatheringDetailDto gatheringDetailDto, List<String> genres, List<Participant> participants, Integer participantCount);

    @Mapping(target = "comments", ignore = true)
    GatheringCommentRs toGatheringCommentRs(GatheringCommentDto commentDto);

    @Mapping(source = "gatheringDoc.id", target = "id")
    @Mapping(source = "gatheringDoc.name", target = "name")
    @Mapping(source = "gatheringDoc.date", target = "date")
    @Mapping(source = "gatheringDoc.theme.title", target = "title")
    @Mapping(source = "gatheringDoc.theme.image", target = "image")
    @Mapping(source = "gatheringDoc.theme.playtime", target = "playtime")
    @Mapping(source = "gatheringDoc.spot.address", target = "address")
    @Mapping(source = "gatheringDoc.theme.genres", target = "genres")
    @Mapping(source = "gatheringDoc.theme.level", target = "level")
    GatheringSearchRs toGatheringSearchRs(GatheringDoc gatheringDoc, GatheringDto gatheringDto);
}