package phanes.replay.gathering.mapper;

import org.mapstruct.Mapper;
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

    GatheringDetailRs toGatheringDetailRs(GatheringDetailDto gatheringDetailDto, List<String> genres, List<Participant> participants, List<GatheringCommentRs> comments);

    GatheringCommentRs toGatheringCommentRs(GatheringCommentDto commentDto);
}