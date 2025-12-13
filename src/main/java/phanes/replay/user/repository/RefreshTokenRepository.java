package phanes.replay.user.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import phanes.replay.user.domain.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    @EntityGraph(attributePaths = {"user"})
    Optional<RefreshToken> findByToken(String refreshToken);

    Optional<RefreshToken> findByUserId(Long userId);
}