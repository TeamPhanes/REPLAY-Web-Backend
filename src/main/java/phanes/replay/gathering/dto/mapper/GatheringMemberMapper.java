package phanes.replay.gathering.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import phanes.replay.gathering.domain.GatheringMember;
import phanes.replay.gathering.dto.response.GatheringMemberRs;

@Mapper(componentModel = "spring")
public interface GatheringMemberMapper {

    @Mapping(target = "nickname", source = "user.nickname")
    @Mapping(target = "image", source = "user.profileImage")
    @Mapping(target = "comment", source = "user.profileComment")
    @Mapping(target = "createdAt", source = "user.createdAt")
    @Mapping(target = "updatedAt", source = "user.updatedAt")
    @Mapping(target = "representAchievement", expression = "java(java.util.Collections.emptyList())")
    GatheringMemberRs toGatheringRs(GatheringMember gatheringMember);
}