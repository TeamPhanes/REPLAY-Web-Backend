package phanes.replay.gathering.persistence.mapper;

import org.apache.ibatis.annotations.Mapper;
import phanes.replay.gathering.dto.query.GatheringQuery;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface GatheringQueryMapper {

    Long countByKeywordAndAddress(String sortBy, String keyword, String city, String state, LocalDateTime startDate, LocalDateTime endDate, String genre);

    List<GatheringQuery> findAllByKeywordAndAddress(Long userId, String sortBy, String keyword, String city, String state, LocalDateTime startDate, LocalDateTime endDate, String genre, Integer limit, Integer offset);

    List<GatheringQuery> findAllByHost(Long userId);
}
