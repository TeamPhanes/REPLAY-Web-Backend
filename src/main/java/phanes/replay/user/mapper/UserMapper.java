package phanes.replay.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import phanes.replay.gathering.dto.MyCommentDto;
import phanes.replay.user.domain.User;
import phanes.replay.user.dto.user.AchievementDto;
import phanes.replay.user.dto.user.MyCommentRs;
import phanes.replay.user.dto.user.ProfileRs;
import phanes.replay.user.dto.user.UserRs;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "profileImage", target = "image")
    UserRs toUserRs(User user);

    ProfileRs toProfileRs(User user, Integer createGatheringCount, Integer visitGatheringCount, Integer visitThemeCount, Integer successThemeCount, List<AchievementDto> achievements);

    MyCommentRs toMyCommentRs(User user, MyCommentDto myCommentDto);
}