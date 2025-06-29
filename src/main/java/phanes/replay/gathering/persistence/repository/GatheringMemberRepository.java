package phanes.replay.gathering.persistence.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import phanes.replay.gathering.domain.GatheringMember;
import phanes.replay.gathering.domain.enums.Role;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface GatheringMemberRepository extends JpaRepository<GatheringMember, Long> {

    Long countByUserId(Long userId);

    Long countByUserIdAndRoleEquals(Long userId, Role role);

    Long countByUserIdAndGatheringIdNotAndRoleEquals(Long userId, Long gatheringId, Role role);

    @EntityGraph(attributePaths = {"user", "gathering"})
    List<GatheringMember> findAllByGatheringIdIn(Set<Long> gatheringIdList);

    @EntityGraph(attributePaths = {"user", "gathering"})
    List<GatheringMember> findAllByGatheringId(Long gatheringId);

    GatheringMember findByUserIdAndGatheringId(Long userId, Long gatheringId);

    @EntityGraph(attributePaths = {"user", "gathering"})
    Optional<GatheringMember> findByUserIdAndGatheringIdAndRoleEquals(Long userId, Long gatheringId, Role role);
}