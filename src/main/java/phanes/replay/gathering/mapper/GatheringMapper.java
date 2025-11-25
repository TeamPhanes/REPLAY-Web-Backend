package phanes.replay.gathering.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import phanes.replay.gathering.dto.GatheringCommentDto;
import phanes.replay.gathering.dto.GatheringDetailDto;
import phanes.replay.gathering.dto.GatheringDto;
import phanes.replay.gathering.dto.response.GatheringCommentRs;
import phanes.replay.gathering.dto.response.GatheringDetailRs;
import phanes.replay.gathering.dto.response.GatheringRs;
import phanes.replay.gathering.dto.response.Participant;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GatheringMapper {

    GatheringRs toGatheringRs(GatheringDto gatheringDto, List<String> genres);

    GatheringDetailRs toGatheringDetailRs(GatheringDetailDto gatheringDetailDto, List<String> genres, List<Participant> participants, Integer participantCount);

    @Mapping(target = "comments", ignore = true)
    GatheringCommentRs toGatheringCommentRs(GatheringCommentDto commentDto);
}