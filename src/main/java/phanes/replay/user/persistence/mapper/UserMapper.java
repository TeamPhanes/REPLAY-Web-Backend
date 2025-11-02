package phanes.replay.user.persistence.mapper;

import org.mapstruct.Mapper;
import phanes.replay.user.domain.User;
import phanes.replay.user.dto.user.UserRs;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserRs toUserRs(User user);
}
