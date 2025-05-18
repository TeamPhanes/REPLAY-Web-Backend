package phanes.replay.user.persistence.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import phanes.replay.user.dto.user.query.UserLikeGatheringQuery;
import phanes.replay.user.dto.user.query.UserParticipantGatheringQuery;

import java.util.List;

@Mapper
public interface UserGatheringQueryMapper {

    List<UserParticipantGatheringQuery> findUserParticipantGathering(@Param("userId") Long userId, @Param("limit") Integer limit, @Param("offset") Integer offset);

    List<UserLikeGatheringQuery> findUserLikeGathering(@Param("userId") Long userId, @Param("limit") Integer limit, @Param("offset") Integer offset);
}
