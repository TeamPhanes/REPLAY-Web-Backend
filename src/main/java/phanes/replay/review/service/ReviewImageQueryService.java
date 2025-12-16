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

    public ReviewImage findByIdAndReviewId(Long id, Long reviewId) {
        return reviewImageRepository.findByIdAndReviewId(id, reviewId).orElseThrow();
    }

    public List<ReviewImage> findAllByReviewId(Long reviewId) {
        return reviewImageRepository.findAllByReviewId(reviewId);
    }

    public List<ReviewImage> findAll(List<Long> deleteImageIds) {
        return reviewImageRepository.findAllById(deleteImageIds);
    }

    public void save(ReviewImage reviewImage) {
        reviewImageRepository.save(reviewImage);
    }

    public void saveAll(List<ReviewImage> reviewImages) {
        reviewImageRepository.saveAll(reviewImages);
    }

    public void delete(ReviewImage reviewImage) {
        reviewImageRepository.delete(reviewImage);
    }

    public void deleteAll(List<ReviewImage> reviewImageList) {
        reviewImageRepository.deleteAll(reviewImageList);
    }
}