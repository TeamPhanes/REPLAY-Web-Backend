package phanes.replay.user.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import phanes.replay.user.domain.User;
import phanes.replay.user.dto.user.UserRs;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "profileImage", target = "image")
    UserRs toUserRs(User user);
}