package phanes.replay.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import phanes.replay.user.domain.User;
import phanes.replay.user.dto.UserDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "user.profileComment", target = "comment")
    @Mapping(source = "user.profileImage", target = "image")
    UserDTO UserToUserDTO(User user, Long totalGathering, Long totalMakeGathering, Long totalTheme, Long successCount, Long failCount, List<String> representAchievement);
}
