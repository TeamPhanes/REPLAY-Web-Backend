package phanes.replay.review.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import phanes.replay.review.dto.query.ReviewQuery;
import phanes.replay.review.dto.response.ReviewRs;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(target = "rating", source = "score")
    @Mapping(target = "playUser", source = "numberOfPlayer")
    @Mapping(target = "user.name", source = "nickname")
    @Mapping(target = "user.image", source = "profileImage")
    ReviewRs toReviewDTO(ReviewQuery review);
}