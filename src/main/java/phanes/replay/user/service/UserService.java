package phanes.replay.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import phanes.replay.gathering.domain.GatheringComment;
import phanes.replay.gathering.domain.GatheringMember;
import phanes.replay.gathering.domain.GatheringScheduleView;
import phanes.replay.gathering.domain.enums.Role;
import phanes.replay.gathering.service.GatheringCommentService;
import phanes.replay.gathering.service.GatheringMemberQueryService;
import phanes.replay.gathering.service.GatheringService;
import phanes.replay.image.service.S3Service;
import phanes.replay.review.service.ReviewQueryService;
import phanes.replay.theme.service.ThemeService;
import phanes.replay.theme.service.ThemeVisitQueryService;
import phanes.replay.user.domain.User;
import phanes.replay.user.dto.user.mapper.UserMapper;
import phanes.replay.user.dto.user.query.UserParticipantGatheringQuery;
import phanes.replay.user.dto.user.query.UserVisitThemeQuery;
import phanes.replay.user.dto.user.response.*;
import phanes.replay.user.persistence.mapper.UserGatheringQueryMapper;
import phanes.replay.user.persistence.mapper.UserThemeQueryMapper;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserQueryService userQueryService;
    private final GatheringMemberQueryService gatheringMemberQueryService;
    private final UserThemeQueryMapper userThemeQueryMapper;
    private final UserGatheringQueryMapper userGatheringQueryMapper;
    private final GatheringCommentService gatheringCommentService;
    private final GatheringService gatheringService;
    private final ThemeService themeService;
    private final ThemeVisitQueryService themeVisitQueryService;
    private final ReviewQueryService reviewQueryService;
    private final S3Service s3Service;
    private final UserMapper userMapper;

    public UserRs getProfileUserInfo(Long userId) {
        User user = userQueryService.findByUserId(userId);
        Long totalGathering = gatheringMemberQueryService.countByUserId(userId);
        Long totalMakeGathering = gatheringMemberQueryService.countByUserIdAndRole(userId, Role.HOST);
        Long totalTheme = themeVisitQueryService.countByUserId(userId);
        Long successCount = reviewQueryService.countBySuccess(true);
        Long failCount = reviewQueryService.countBySuccess(false);
        return userMapper.UserToUserDTO(user, totalGathering, totalMakeGathering, totalTheme, successCount, failCount, List.of(""));
    }

    @Transactional
    public void updateUser(Long userId, MultipartFile image, String nickname, String comment, Boolean emailMark, Boolean genderMark) {
        User user = userQueryService.findByUserId(userId);
        String imageUrl = image == null ? user.getProfileImage() : s3Service.uploadImage("user/" + UUID.randomUUID() + ".png", image);
        user.updateUserInfo(imageUrl, nickname, comment, emailMark, genderMark);
        userQueryService.save(user);
    }

    public List<UserVisitThemeRs> getMyVisitTheme(Long userId) {
        List<UserVisitThemeQuery> userPlayingThemeList = userThemeQueryMapper.findUserVisitThemes(userId);
        return userPlayingThemeList.stream().map(userMapper::toUserVisitThemeRs).toList();
    }

    public OtherUserRs getUserByNickname(String nickname) {
        User user = userQueryService.findByUsername(nickname);
        Long totalGathering = gatheringMemberQueryService.countByUserId(user.getId());
        Long totalMakeGathering = gatheringMemberQueryService.countByUserIdAndRole(user.getId(), Role.HOST);
        Long totalTheme = themeVisitQueryService.countByUserId(user.getId());
        Long successCount = reviewQueryService.countBySuccess(true);
        Long failCount = reviewQueryService.countBySuccess(false);
        return userMapper.UserToOtherUserDTO(user, totalGathering, totalMakeGathering, totalTheme, successCount, failCount, List.of(""));
    }

    public List<UserParticipatingGatheringRs> getMyParticipatingGathering(Long userId, Pageable pageable) {
        List<UserParticipantGatheringQuery> userParticipantGatheringQuery = userGatheringQueryMapper.findUserParticipantGathering(userId, pageable);
        Set<Long> gatheringIdList = userParticipantGatheringQuery.stream().map(UserParticipantGatheringQuery::getGatheringId).collect(Collectors.toSet());
        Map<Long, List<GatheringMember>> collect = gatheringMemberQueryService.getMemberList(gatheringIdList).stream().collect(Collectors.groupingBy(gm -> gm.getGathering().getId()));
        List<UserParticipatingGatheringRs> myParticipatingGathering = userParticipantGatheringQuery.stream().map(userMapper::ParticipatingGatheringViewToParticipatingGatheringDTO).toList();
        myParticipatingGathering.forEach(pg ->
                pg.setParticipants(
                        collect.get(pg.getGatheringId()).stream()
                                .map(gm -> UserParticipatingGatheringRs.ParticipatingUserDTO
                                        .builder()
                                        .name(gm.getUser().getNickname())
                                        .image(gm.getUser().getProfileImage())
                                        .build())
                                .toList()));
        return myParticipatingGathering;
    }

    public Map<LocalDate, List<UserCommentRs>> getMyComment(Long userId, Pageable pageable) {
        Page<GatheringComment> myComment = gatheringCommentService.getMyComment(userId, pageable);
        return myComment
                .stream()
                .map(userMapper::GatheringCommentToUserCommentDTO)
                .collect(Collectors.groupingBy(uc -> uc.getCreatedAt().toLocalDate(), LinkedHashMap::new, Collectors.toList()));
    }

    public List<UserLikeGatheringRs> getMyLikeGathering(Long userId, Pageable pageable) {
        return gatheringService.getUserLikeGathering(userId, pageable).stream().map(userMapper::LikeGatheringViewToUserLikeGatheringDTO).toList();
    }

    public List<UserLikeThemeRs> getMyLikeTheme(Long userId, Pageable pageable) {
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
