package phanes.replay.review.persistence.mapper;

import org.apache.ibatis.annotations.Mapper;
import phanes.replay.review.dto.query.ReviewQuery;

import java.util.List;

@Mapper
public interface ReviewQueryMapper {

    List<ReviewQuery> findAllByThemeId(Long userId, Long themeId, Integer limit, Integer offset);
}