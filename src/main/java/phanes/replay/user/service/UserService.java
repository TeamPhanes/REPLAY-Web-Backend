package phanes.replay.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import phanes.replay.exception.ImageUploadFailException;
import phanes.replay.exception.UserNotFoundException;
import phanes.replay.gathering.domain.*;
import phanes.replay.gathering.service.GatheringCommentService;
import phanes.replay.gathering.service.GatheringMemberService;
import phanes.replay.gathering.service.GatheringService;
import phanes.replay.image.service.S3Service;
import phanes.replay.review.domain.Review;
import phanes.replay.review.service.ReviewService;
import phanes.replay.theme.domain.ParticipatingThemeView;
import phanes.replay.theme.service.ParticipatingThemeService;
import phanes.replay.theme.service.ThemeService;
import phanes.replay.user.domain.User;
import phanes.replay.user.dto.*;
import phanes.replay.user.mapper.UserMapper;
import phanes.replay.user.repository.UserRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final GatheringMemberService gatheringMemberService;
    private final GatheringCommentService gatheringCommentService;
    private final GatheringService gatheringService;
    private final ThemeService themeService;
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

    public List<UserPlayThemeDTO> getMyPlayingTheme(Long userId) {
        List<ParticipatingThemeView> userPlayingThemeList = participatingThemeService.getUserPlayingThemeList(userId);
        return userPlayingThemeList.stream().map(userMapper::ParticipatingThemeViewToUserPlayThemeDTO).toList();
    }

    public void updateThemeReview(Long userId, UserPlayThemeDTO theme) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("user not found"));
        Review review = reviewService.getReviewById(theme.getReviewId(), user.getId());
        review.updateReview(theme.getMyRating(), theme.getHint(), theme.getNumberOfPlayer(), theme.getThemeReview(), theme.getLevelReview(), theme.getStoryReview(), theme.getReviewComment(), theme.getSuccess());
        reviewService.updateReview(review);
    }

    public OtherUserDTO getUserByNickname(String nickname) {
        User user = userRepository.findByNickname(nickname).orElseThrow(() -> new UserNotFoundException("user not found"));
        Long totalGathering = gatheringMemberService.getTotalGatheringCount(user.getId());
        Long totalMakeGathering = gatheringMemberService.getTotalMakeGatheringCount(user.getId(), Role.HOST);
        Long totalTheme = participatingThemeService.getTotalThemeCount(user.getId());
        Long successCount = reviewService.getCountBySuccess(true);
        Long failCount = reviewService.getCountBySuccess(false);
        return userMapper.UserToOtherUserDTO(user, totalGathering, totalMakeGathering, totalTheme, successCount, failCount, List.of(""));
    }

    public List<UserParticipatingGatheringDTO> getMyParticipatingGathering(Long userId, Pageable pageable) {
        List<ParticipatingGatheringView> participatingGatheringView = gatheringMemberService.getParticipatingGatheringView(userId, pageable);
        Set<Long> gatheringIdList = participatingGatheringView.stream().map(ParticipatingGatheringView::getGatheringId).collect(Collectors.toSet());
        Map<Long, List<Gathering_Member>> collect = gatheringMemberService.getMemberList(gatheringIdList).stream().collect(Collectors.groupingBy(gm -> gm.getGathering().getId()));
        List<UserParticipatingGatheringDTO> myParticipatingGathering = participatingGatheringView.stream().map(userMapper::ParticipatingGatheringViewToParticipatingGatheringDTO).toList();
        myParticipatingGathering.forEach(pg ->
                pg.setParticipants(
                        collect.get(pg.getGatheringId()).stream()
                                .map(gm -> UserParticipatingGatheringDTO.ParticipatingUserDTO
                                        .builder()
                                        .name(gm.getUser().getNickname())
                                        .image(gm.getUser().getProfileImage())
                                        .build())
                                .toList()));
        return myParticipatingGathering;
    }

    public Map<LocalDate, List<UserCommentDTO>> getMyComment(Long userId, Pageable pageable) {
        List<GatheringComment> myComment = gatheringCommentService.getMyComment(userId, pageable);
        List<UserCommentDTO> commentDTOList = myComment.stream().map(userMapper::GatheringCommentToUserCommentDTO).toList();
        return commentDTOList.stream().collect(Collectors.groupingBy(uc -> uc.getCreatedAt().toLocalDate()));
    }

    public List<UserLikeGatheringDTO> getMyLikeGathering(Long userId, Pageable pageable) {
        return gatheringService.getUserLikeGathering(userId, pageable).stream().map(userMapper::LikeGatheringViewToUserLikeGatheringDTO).toList();
    }

    public List<UserLikeThemeDTO> getMyLikeTheme(Long userId, Pageable pageable) {
        return themeService.getUserLikeTheme(userId, pageable).stream().map(userMapper::ThemeLikeViewToUserLikeThemeDTO).toList();
    }

    public Map<LocalDate, List<UserScheduleDTO>> getMySchedule(Long userId) {
        List<GatheringScheduleView> userSchedule = gatheringService.getUserSchedule(userId);
        return userSchedule
                .stream()
                .collect(Collectors.groupingBy(gsv -> gsv.getDateTime().toLocalDate(),
                        Collectors.mapping(userMapper::GatheringScheduleViewToUserScheduleDTO,
                                Collectors.toList())));
    }
}
