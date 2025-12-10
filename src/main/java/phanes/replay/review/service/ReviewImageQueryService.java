package phanes.replay.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import phanes.replay.review.domain.ReviewImage;
import phanes.replay.review.repository.ReviewImageRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewImageQueryService {

    private final ReviewImageRepository reviewImageRepository;

    public void saveAll(List<ReviewImage> reviewImages) {
        reviewImageRepository.saveAll(reviewImages);
    }

    public List<ReviewImage> findAllByReviewId(Long reviewId) {
        return reviewImageRepository.findAllByReviewId(reviewId);
    }

    public void deleteAll(List<ReviewImage> reviewImageList) {
        reviewImageRepository.deleteAll(reviewImageList);
    }
}