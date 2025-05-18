package phanes.replay.user.persistence.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import phanes.replay.user.dto.user.query.UserLikeThemeQuery;
import phanes.replay.user.dto.user.query.UserVisitThemeQuery;

import java.util.List;

@Mapper
public interface UserThemeQueryMapper {

    List<UserVisitThemeQuery> findUserVisitThemes(@Param("userId") Long userId, @Param("limit") Integer limit, @Param("offset") Integer offset);
    List<UserLikeThemeQuery> findUserLikeThemes(@Param("userId") Long userId, @Param("limit") Integer limit, @Param("offset") Integer offset);
}
