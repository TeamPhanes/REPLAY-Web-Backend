package phanes.replay.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import phanes.replay.user.domain.User;
import phanes.replay.user.dto.UserDTO;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "profileComment", target = "comment")
    @Mapping(source = "profileImage", target = "image")
    UserDTO UserToUserDTO(User user);
}
