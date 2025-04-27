package phanes.replay.roomescape.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomEscapeParticipateRepository extends JpaRepository<RoomEscapeParticipateRepository, Long> {
    Long countByUserId(Long userId);
}
