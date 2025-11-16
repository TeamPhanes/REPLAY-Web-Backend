package phanes.replay.review.mapper;

import org.mapstruct.Mapper;
import phanes.replay.review.dto.response.ReviewCountSummary;
import phanes.replay.review.dto.response.ReviewDetailRs;
import phanes.replay.review.dto.response.ReviewRs;
import phanes.replay.review.dto.response.UserEvaluation;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    ReviewRs toReviewRs(Double avgScore, Long createdGatheringCount, ReviewCountSummary reviewCountSummary, UserEvaluation userEvaluation, List<ReviewDetailRs> contents);
}