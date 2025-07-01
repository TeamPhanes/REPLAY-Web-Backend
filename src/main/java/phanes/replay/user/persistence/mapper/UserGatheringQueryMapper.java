package phanes.replay.user.persistence.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import phanes.replay.user.dto.user.query.UserLikeGatheringQuery;
import phanes.replay.user.dto.user.query.UserParticipantGatheringQuery;
import phanes.replay.user.dto.user.query.UserScheduleQuery;

import java.util.List;

@Mapper
public interface UserGatheringQueryMapper {

    List<UserParticipantGatheringQuery> findUserParticipantGatheringById(@Param("userId") Long userId, @Param("limit") Integer limit, @Param("offset") Integer offset);

    List<UserLikeGatheringQuery> findUserLikeGatheringById(@Param("userId") Long userId, @Param("limit") Integer limit, @Param("offset") Integer offset);

    List<UserScheduleQuery> findScheduleById(Long userId);
}