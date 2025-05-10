package phanes.replay.review.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import phanes.replay.review.domain.Review;
import phanes.replay.review.dto.response.ReviewRs;

@Mapper(componentModel = "spring")
public interface ReviewMapper {


    @Mapping(target = "rating", source = "score")
    @Mapping(target = "playUser", source = "numberOfPlayer")
    @Mapping(target = "user.name", source = "user.nickname")
    @Mapping(target = "user.image", source = "user.profileImage")
    @Mapping(target = "image", expression = "java(review.getImages().isEmpty() ? \"\" : review.getImages().getFirst().getUrl())")
    ReviewRs ReviewToReviewDTO(Review review);
}
