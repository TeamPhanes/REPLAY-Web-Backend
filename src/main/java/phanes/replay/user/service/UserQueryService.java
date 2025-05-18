package phanes.replay.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import phanes.replay.exception.UserNotFoundException;
import phanes.replay.user.domain.User;
import phanes.replay.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserQueryService {

    private final UserRepository userRepository;

    public User findByUserId(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("user not found"));
    }

    public User findByUsername(String nickname) {
        return userRepository.findByNickname(nickname).orElseThrow(() -> new UserNotFoundException("user not found"));
    }

    public void save(User user) {
        userRepository.save(user);
    }
}
