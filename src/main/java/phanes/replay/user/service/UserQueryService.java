package phanes.replay.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import phanes.replay.exception.UserNotFoundException;
import phanes.replay.user.domain.User;
import phanes.replay.user.persistence.repository.UserRepository;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserQueryService {

    private final UserRepository userRepository;

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found", Map.of("id", id)));
    }

    public User findByNickname(String nickname) {
        return userRepository.findByNickname(nickname)
                .orElseThrow(() -> new UserNotFoundException("User not found", Map.of("nickname", nickname)));
    }

    public void save(User user) {
        userRepository.save(user);
    }
}