package phanes.replay.gathering.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import phanes.replay.gathering.domain.Gathering_Member;
import phanes.replay.gathering.domain.Role;

import java.util.List;
import java.util.Set;

@Repository
public interface Gathering_MemberRepository extends JpaRepository<Gathering_Member, Long> {

    Long countByUserIdAndRoleEquals(Long userId, Role role);
    @Query("SELECT gm FROM Gathering_Member gm JOIN gm.user u JOIN gm.gathering g WHERE gm.gathering.id IN :gatheringIdList")
    List<Gathering_Member> findAllByMember(Set<Long> gatheringIdList);
    @Query("SELECT gm FROM Gathering_Member gm JOIN gm.user u WHERE gm.gathering.id = :gatheringId")
    List<Gathering_Member> findAllByGatheringId(Long gatheringId);
    Gathering_Member findByUserIdAndGatheringId(Long userId, Long gatheringId);
}
