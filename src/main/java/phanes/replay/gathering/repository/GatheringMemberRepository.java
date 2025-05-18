package phanes.replay.gathering.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import phanes.replay.gathering.domain.GatheringMember;
import phanes.replay.gathering.domain.enums.Role;

import java.util.List;
import java.util.Set;

@Repository
public interface GatheringMemberRepository extends JpaRepository<GatheringMember, Long> {

    Long countByUserIdAndRoleEquals(Long userId, Role role);
    @Query("SELECT gm FROM GatheringMember gm JOIN gm.user u JOIN gm.gathering g WHERE gm.gathering.id IN :gatheringIdList")
    List<GatheringMember> findAllByMember(Set<Long> gatheringIdList);
    @Query("SELECT gm FROM GatheringMember gm JOIN gm.user u WHERE gm.gathering.id = :gatheringId")
    List<GatheringMember> findAllByGatheringId(Long gatheringId);
    GatheringMember findByUserIdAndGatheringId(Long userId, Long gatheringId);
}
