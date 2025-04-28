package phanes.replay.roomescape.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import phanes.replay.roomescape.domain.RoomEscapeParticipate;

@Repository
public interface RoomEscapeParticipateRepository extends JpaRepository<RoomEscapeParticipate, Long> {
    Long countByUserId(Long userId);
}
