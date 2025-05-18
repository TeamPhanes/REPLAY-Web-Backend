package phanes.replay.user.persistence.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;
import phanes.replay.user.dto.user.query.UserVisitThemeQuery;

import java.util.List;

@Mapper
public interface UserThemeQueryMapper {

    List<UserVisitThemeQuery> findUserVisitThemes(@Param("userId") Long userId);
}
