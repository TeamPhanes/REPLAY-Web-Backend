package phanes.replay.review.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import phanes.replay.review.domain.Review;
import phanes.replay.review.dto.response.ReviewRs;

@Mapper(componentModel = "spring")
public interface ReviewMapper {


    @Mapping(target = "rating", source = "score")
    @Mapping(target = "playUser", source = "numberOfPlayer")
    @Mapping(target = "image", source = "user.profileImage")
    @Mapping(target = "user.name", source = "user.nickname")
    @Mapping(target = "user.image", source = "user.profileImage")
    ReviewRs ReviewToReviewDTO(Review review);
}
