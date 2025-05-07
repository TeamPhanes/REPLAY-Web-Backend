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

    Long countByUserId(Long userId);
    Long countByUserIdAndRoleEquals(Long userId, Role role);
    @Query("SELECT u FROM Gathering_Member gm JOIN gm.user u JOIN gm.gathering g WHERE gm.gathering.id IN :gatheringIdList")
    List<Gathering_Member> findAllByMember(Set<Long> gatheringIdList);
}
