package phanes.replay.gathering.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phanes.replay.gathering.domain.GatheringMember;

public interface GatheringMemberRepository extends JpaRepository<GatheringMember,Long> {
}