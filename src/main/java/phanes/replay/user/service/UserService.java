package phanes.replay.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import phanes.replay.exception.ImageUploadFailException;
import phanes.replay.exception.UserNotFoundException;
import phanes.replay.gathering.domain.Role;
import phanes.replay.gathering.service.GatheringMemberService;
import phanes.replay.image.service.S3Service;
import phanes.replay.review.ReviewService;
import phanes.replay.theme.service.ParticipatingThemeService;
import phanes.replay.user.domain.User;
import phanes.replay.user.dto.UserDTO;
import phanes.replay.user.mapper.UserMapper;
import phanes.replay.user.repository.UserRepository;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final GatheringMemberService gatheringMemberService;
    private final ParticipatingThemeService participatingThemeService;
    private final ReviewService reviewService;
    private final S3Service s3Service;
    private final UserMapper userMapper;

    public UserDTO getUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        Long totalGathering = gatheringMemberService.getTotalGatheringCount(userId);
        Long totalMakeGathering = gatheringMemberService.getTotalMakeGatheringCount(userId, Role.HOST);
        Long totalTheme = participatingThemeService.getTotalThemeCount(userId);
        Long successCount = reviewService.getCountBySuccess(true);
        Long failCount = reviewService.getCountBySuccess(false);
        return userMapper.UserToUserDTO(user, totalGathering, totalMakeGathering, totalTheme, successCount, failCount, List.of(""));
    }

    @Transactional
    public void updateUser(Long userId, MultipartFile image, String nickname, String comment, Boolean emailMark, Boolean genderMark) {
        String imageUrl;
        try {
            imageUrl = s3Service.uploadImage("replay", "images/" + UUID.randomUUID() + ".png", image);
        } catch (IOException e) {
            throw new ImageUploadFailException("image upload fail");
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("user not found"));
        user.updateUserInfo(imageUrl, nickname, comment, emailMark, genderMark);
        userRepository.save(user);
    }
}
