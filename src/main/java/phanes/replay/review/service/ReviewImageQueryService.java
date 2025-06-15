package phanes.replay.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import phanes.replay.review.domain.ReviewImage;
import phanes.replay.review.persistence.repository.ReviewImageRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewImageQueryService {

    private final ReviewImageRepository reviewImageRepository;

    public List<ReviewImage> findByReviewId(Long reviewId) {
        return reviewImageRepository.findByReviewId(reviewId);
    }

    public void save(ReviewImage reviewImage) {
        reviewImageRepository.save(reviewImage);
    }

    public void deleteAll(List<ReviewImage> reviewImageList) {
        reviewImageRepository.deleteAll(reviewImageList);
    }
}
