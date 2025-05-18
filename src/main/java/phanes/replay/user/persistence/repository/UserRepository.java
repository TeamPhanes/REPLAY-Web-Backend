package phanes.replay.user.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import phanes.replay.user.domain.User;
import phanes.replay.user.domain.enums.SocialType;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findBySocialIdAndSocialType(String socialId, SocialType socialType);
    Optional<User> findByNickname(String nickname);
}
