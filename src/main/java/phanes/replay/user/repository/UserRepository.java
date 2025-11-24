package phanes.replay.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phanes.replay.user.domain.User;
import phanes.replay.user.domain.enums.SocialType;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findBySocialIdAndSocialType(String socialId, SocialType socialType);

    Optional<User> findByNickname(String nickname);
}