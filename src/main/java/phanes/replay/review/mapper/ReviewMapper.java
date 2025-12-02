package phanes.replay.review.mapper;

import org.mapstruct.Mapper;
import phanes.replay.review.dto.ReviewDto;
import phanes.replay.review.dto.response.ReviewCountSummary;
import phanes.replay.review.dto.response.ReviewRs;
import phanes.replay.review.dto.response.ReviewSummary;
import phanes.replay.review.dto.response.UserEvaluation;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    ReviewSummary toReviewSummary(Double avgScore, Long createdGatheringCount, ReviewCountSummary reviewCountSummary, UserEvaluation userEvaluation);

    ReviewRs toReviewRs(ReviewDto reviewDto, List<String> images);
}