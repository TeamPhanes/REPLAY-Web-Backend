package phanes.replay.gathering.persistence.mapper;

import org.apache.ibatis.annotations.Mapper;
import phanes.replay.gathering.dto.query.GatheringQuery;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface GatheringQueryMapper {

    List<GatheringQuery> findAllByKeywordAndAddress(Long userId, String sortBy, String keyword, String city, String state, LocalDateTime date, String genre, Integer limit, Integer offset);
}
