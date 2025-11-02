package phanes.replay.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import phanes.replay.user.dto.user.UserRs;
import phanes.replay.user.persistence.mapper.UserMapper;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserQueryService userQueryService;
    private final UserMapper userMapper;

    public UserRs findByUserId(Long userId) {
        return userMapper.toUserRs(userQueryService.findById(userId));
    }
}