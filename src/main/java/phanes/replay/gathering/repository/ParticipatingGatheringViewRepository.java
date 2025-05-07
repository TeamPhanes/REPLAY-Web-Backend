package phanes.replay.gathering.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import phanes.replay.gathering.domain.ParticipatingGatheringView;

import java.util.List;

public interface ParticipatingGatheringViewRepository extends JpaRepository<ParticipatingGatheringView, Long> {

    List<ParticipatingGatheringView> findAllByUserId(Long userId, Pageable pageable);
}
