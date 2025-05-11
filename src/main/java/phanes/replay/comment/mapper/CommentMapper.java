package phanes.replay.comment.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import phanes.replay.comment.dto.response.CommentRs;
import phanes.replay.gathering.domain.GatheringComment;

@Mapper(componentModel = "spring")
public interface CommentMapper {


    @Mapping(target = "nickname", source = "user.nickname")
    CommentRs toCommentRs(GatheringComment comment);
}
