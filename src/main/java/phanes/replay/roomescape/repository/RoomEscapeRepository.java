package phanes.replay.roomescape.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phanes.replay.roomescape.domain.RoomEscape;

public interface RoomEscapeRepository extends JpaRepository<RoomEscape, Long> {
}
