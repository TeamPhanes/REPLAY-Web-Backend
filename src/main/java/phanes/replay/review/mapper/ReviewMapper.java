package phanes.replay.review.mapper;

import org.mapstruct.Mapper;
import phanes.replay.review.dto.response.ReviewCountStat;
import phanes.replay.review.dto.response.ReviewDetailRs;
import phanes.replay.review.dto.response.ReviewRs;
import phanes.replay.review.dto.response.UserEvaluation;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    ReviewRs toReviewRs(Double avgScore, Long createdGatheringCount, List<ReviewCountStat> reviewCount, UserEvaluation userEvaluation, List<ReviewDetailRs> contents);
}
