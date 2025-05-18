package phanes.replay.user.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import phanes.replay.user.domain.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String refreshToken);
    Optional<RefreshToken> findByUserId(Long userId);
}
