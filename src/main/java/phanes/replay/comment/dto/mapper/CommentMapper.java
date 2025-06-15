package phanes.replay.comment.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import phanes.replay.comment.dto.response.CommentRs;
import phanes.replay.gathering.domain.GatheringComment;

@Mapper(componentModel = "spring")
public interface CommentMapper {


    @Mapping(target = "reComments", ignore = true)
    @Mapping(target = "commentId", source = "id")
    @Mapping(target = "nickname", source = "user.nickname")
    @Mapping(target = "image", source = "user.profileImage")
    CommentRs toCommentRs(GatheringComment comment);

    @Mapping(target = "reCommentId", source = "id")
    @Mapping(target = "nickname", source = "user.nickname")
    @Mapping(target = "image", source = "user.profileImage")
    CommentRs.ReCommentRs toReCommentRs(GatheringComment comment);
}
