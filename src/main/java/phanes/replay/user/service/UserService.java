package phanes.replay.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import phanes.replay.exception.ImageUploadFailException;
import phanes.replay.exception.UserNotFoundException;
import phanes.replay.gathering.domain.Role;
import phanes.replay.gathering.repository.Gathering_MemberRepository;
import phanes.replay.image.service.S3Service;
import phanes.replay.review.repository.ReviewRepository;
import phanes.replay.roomescape.repository.RoomEscapeParticipateRepository;
import phanes.replay.user.domain.User;
import phanes.replay.user.dto.UserDTO;
import phanes.replay.user.mapper.UserMapper;
import phanes.replay.user.repository.UserRepository;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final Gathering_MemberRepository gatheringMemberRepository;
    private final RoomEscapeParticipateRepository roomEscapeParticipateRepository;
    private final ReviewRepository reviewRepository;
    private final S3Service s3Service;
    private final UserMapper userMapper;

    public UserDTO getUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        Long totalGathering = gatheringMemberRepository.countByUserId(userId);
        Long totalMakeGathering = gatheringMemberRepository.countByUserIdAndRoleEquals(userId, Role.HOST);
        Long totalRoomEscape = roomEscapeParticipateRepository.countByUserId(userId);
        Long successCount = reviewRepository.countBySuccess(true);
        Long failCount = reviewRepository.countBySuccess(false);
        return userMapper.UserToUserDTO(user, totalGathering, totalMakeGathering, totalRoomEscape, successCount, failCount);
    }

    @Transactional
    public void updateUser(Long userId, MultipartFile image, String nickname, String comment) {
        String imageUrl;
        try {
            imageUrl = s3Service.uploadImage("replay", "images/" + UUID.randomUUID() + ".png", image);
        } catch (IOException e) {
            throw new ImageUploadFailException("image upload fail");
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("user not found"));
        user.updateUserInfo(imageUrl, nickname, comment);
        userRepository.save(user);
    }
}
