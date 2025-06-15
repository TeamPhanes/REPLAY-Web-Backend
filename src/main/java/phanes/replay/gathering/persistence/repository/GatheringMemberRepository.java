package phanes.replay.gathering.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
    @Query("SELECT gm FROM GatheringMember gm JOIN gm.user u JOIN gm.gathering g WHERE gm.gathering.id IN :gatheringIdList")
    List<GatheringMember> findAllByMember(Set<Long> gatheringIdList);
    @Query("SELECT gm FROM GatheringMember gm JOIN FETCH gm.user u WHERE gm.gathering.id = :gatheringId")
    List<GatheringMember> findAllByGatheringIdWithUser(Long gatheringId);
    @Query("SELECT gm FROM GatheringMember gm JOIN gm.user u JOIN gm.gathering g WHERE gm.gathering.id = :gatheringId")
    List<GatheringMember> findAllByGatheringIdWithUserAndGathering(Long gatheringId);
    GatheringMember findByUserIdAndGatheringId(Long userId, Long gatheringId);
    @Query("SELECT gm FROM GatheringMember gm JOIN FETCH gm.gathering g JOIN FETCH gm.user u WHERE u.id = :userId and g.id = :gatheringId and gm.role = :role")
    Optional<GatheringMember> findByUserIdAndGatheringIdAndRoleEquals(Long userId, Long gatheringId, Role role);
}
