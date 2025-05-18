package phanes.replay.user.persistence.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Pageable;
import phanes.replay.user.dto.user.query.UserParticipantGatheringQuery;

import java.util.List;

@Mapper
public interface UserGatheringQueryMapper {

    List<UserParticipantGatheringQuery> findUserParticipantGathering(Long userId, Pageable pageable);
}
