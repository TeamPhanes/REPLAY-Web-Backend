package phanes.replay.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import phanes.replay.user.dto.UserDTO;
import phanes.replay.user.mapper.UserMapper;
import phanes.replay.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDTO getUser(Long userId) {
        return userMapper.UserToUserDTO(userRepository.findById(userId).orElseThrow());
    }
}
