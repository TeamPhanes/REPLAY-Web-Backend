package phanes.replay.user.persistence.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import phanes.replay.user.dto.user.query.UserVisitThemeQuery;

@Mapper
public interface UserThemeQueryMapper {

    Page<UserVisitThemeQuery> findUserVisitThemes(@Param("userId") Long userId, @Param("limit") Integer limit, @Param("offset") Integer offset);
}
