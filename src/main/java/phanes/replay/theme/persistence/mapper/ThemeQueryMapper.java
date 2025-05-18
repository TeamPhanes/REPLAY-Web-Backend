package phanes.replay.theme.persistence.mapper;

import org.apache.ibatis.annotations.Mapper;
import phanes.replay.theme.dto.query.ThemeQuery;

import java.util.List;

@Mapper
public interface ThemeQueryMapper {

    List<ThemeQuery> findAllByKeywordAndAddress(Long userId, String sortBy, String keyword, String city, String state, String genre, Integer limit, Integer offset);
}
