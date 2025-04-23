package phanes.replay.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import phanes.replay.user.client.UserClient;
import phanes.replay.user.dto.UserDTO;
import phanes.replay.user.dto.UserResponse;
import phanes.replay.user.mapper.UserMapper;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserClient userClient;
    private final UserMapper userMapper;

    public UserDTO getUser(String accessToken) {
        UserResponse userInfo = userClient.getUserInfo(accessToken);
        // TODO get roomescape and gathering info
        return userMapper.userToUserDTO(userInfo);
    }

    public void UpdateUser(String accessToken, MultipartFile image, String nickname, String comment) {
        // TODO s3 image upload
        String imageUrl = null;
        userClient.patchUserInfo(accessToken, imageUrl, nickname, comment);
    }
}