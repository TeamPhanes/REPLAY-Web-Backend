package phanes.replay.gathering.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import phanes.replay.gathering.domain.GatheringMember;
import phanes.replay.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface GatheringMemberRepository extends JpaRepository<GatheringMember,Long> {

    List<GatheringMember> user(User user);

    Optional<GatheringMember> findByUserIdAndGatheringId(Long userId, Long gatheringId);

    @EntityGraph(attributePaths = {"user"})
    List<GatheringMember> findAllByGatheringId(Long gatheringId);
}