package phanes.replay.user.mapper;

import org.mapstruct.Mapper;
import phanes.replay.user.dto.UserDTO;
import phanes.replay.user.dto.UserResponse;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO userToUserDTO(UserResponse user);
}
