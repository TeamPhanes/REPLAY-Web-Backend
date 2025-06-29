package phanes.replay.gathering.persistence.mapper;

import org.apache.ibatis.annotations.Mapper;
import phanes.replay.gathering.dto.query.GatheringQuery;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface GatheringQueryMapper {

    Long countByKeywordAndCityAndStateAndDateAndGenre(String sortBy, String keyword, String city, String state, LocalDateTime startDate, LocalDateTime endDate, String genre);

    Long countByDate(Long gatheringId, LocalDateTime startDate, LocalDateTime endDate);

    List<GatheringQuery> findAllByKeywordAndCityAndStateAndDateAndGenre(Long userId, String sortBy, String keyword, String city, String state, LocalDateTime startDate, LocalDateTime endDate, String genre, Integer limit, Integer offset);

    List<GatheringQuery> findAllByHost(Long userId, Long gatheringId, Long hostId);

    List<GatheringQuery> findAllByDate(Long userId, Long gatheringId, LocalDateTime startDate, LocalDateTime endDate);
}