package phanes.replay.gathering.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import phanes.replay.gathering.domain.Gathering_Member;
import phanes.replay.gathering.domain.Role;

@Repository
public interface Gathering_MemberRepository extends JpaRepository<Gathering_Member, Long> {

    Long countByUserId(Long userId);
    Long countByUserIdAndRoleEquals(Long userId, Role role);
}
