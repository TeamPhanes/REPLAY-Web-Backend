package phanes.replay.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import phanes.replay.gathering.repository.GatheringRepository;
import phanes.replay.review.dto.response.ReviewCountStat;
import phanes.replay.review.dto.response.ReviewDetailRs;
import phanes.replay.review.dto.response.ReviewRs;
import phanes.replay.review.dto.response.UserEvaluation;
import phanes.replay.review.mapper.ReviewMapper;
import phanes.replay.review.repository.ReviewJooqRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewJooqRepository reviewJooqRepository;
    private final GatheringRepository gatheringRepository;
    private final ReviewMapper reviewMapper;

    public ReviewRs findAllByThemeId(Long userId, Pageable pageable, Long themeId) {
        List<ReviewDetailRs> reviewDetailList = reviewJooqRepository.findAllByThemeId(userId, pageable, themeId);
        Double avgScore = reviewJooqRepository.aggregateByThemeId(themeId);
        Long createdGatheringCount = gatheringRepository.countByThemeId(themeId);
        UserEvaluation userEvaluation = reviewJooqRepository.findEvaluationByThemeId(themeId);
        List<ReviewCountStat> reviewCount = reviewJooqRepository.findScoreCountByThemeId(themeId);
        return reviewMapper.toReviewRs(avgScore, createdGatheringCount, reviewCount, userEvaluation, reviewDetailList);
    }
}