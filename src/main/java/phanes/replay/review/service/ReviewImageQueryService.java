package phanes.replay.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import phanes.replay.review.domain.ReviewImage;
import phanes.replay.review.persistence.repository.ReviewImageRepository;

@Service
@RequiredArgsConstructor
public class ReviewImageQueryService {

    private final ReviewImageRepository reviewImageRepository;

    public void save(ReviewImage reviewImage) {
        reviewImageRepository.save(reviewImage);
    }
}
