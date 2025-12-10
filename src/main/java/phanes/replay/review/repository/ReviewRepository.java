package phanes.replay.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phanes.replay.review.domain.Review;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review,Long> {

    Optional<Review> findByThemeVisitId(Long themeVisitId);
}