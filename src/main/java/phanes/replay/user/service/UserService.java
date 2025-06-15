package phanes.replay.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import phanes.replay.common.dto.mapper.PageMapper;
import phanes.replay.common.dto.response.Page;
import phanes.replay.exception.ImageUploadFailException;
import phanes.replay.gathering.domain.GatheringComment;
import phanes.replay.gathering.domain.GatheringMember;
import phanes.replay.gathering.domain.enums.Role;
import phanes.replay.gathering.service.GatheringCommentQueryService;
import phanes.replay.gathering.service.GatheringLikeQueryService;
import phanes.replay.gathering.service.GatheringMemberQueryService;
import phanes.replay.image.service.S3Service;
import phanes.replay.review.service.ReviewQueryService;
import phanes.replay.theme.service.ThemeLikeQueryService;
import phanes.replay.theme.service.ThemeVisitQueryService;
import phanes.replay.user.domain.User;
import phanes.replay.user.dto.user.mapper.UserMapper;
import phanes.replay.user.dto.user.query.UserParticipantGatheringQuery;
import phanes.replay.user.dto.user.query.UserScheduleQuery;
import phanes.replay.user.dto.user.query.UserVisitThemeQuery;
import phanes.replay.user.dto.user.response.*;
import phanes.replay.user.persistence.mapper.UserGatheringQueryMapper;
import phanes.replay.user.persistence.mapper.UserThemeQueryMapper;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final GatheringCommentQueryService gatheringCommentQueryService;
    private final GatheringMemberQueryService gatheringMemberQueryService;
    private final GatheringLikeQueryService gatheringLikeQueryService;
    private final ThemeVisitQueryService themeVisitQueryService;
    private final ThemeLikeQueryService themeLikeQueryService;
    private final ReviewQueryService reviewQueryService;
    private final UserQueryService userQueryService;
    private final UserGatheringQueryMapper userGatheringQueryMapper;
    private final UserThemeQueryMapper userThemeQueryMapper;
    private final PageMapper<List<UserVisitThemeRs>> visitThemePageMapper;
    private final PageMapper<List<UserParticipatingGatheringRs>> participatingGatheringPageMapper;
    private final PageMapper<Map<LocalDate, List<UserCommentRs>>> commentPageMapper;
    private final PageMapper<List<UserLikeGatheringRs>> likeGatheringPageMapper;
    private final PageMapper<List<UserLikeThemeRs>> likeThemePageMapper;
    private final UserMapper userMapper;
    private final S3Service s3Service;

    public UserRs getProfileUserInfo(Long userId) {
        User user = userQueryService.findById(userId);
        Long totalGathering = gatheringMemberQueryService.countByUserId(userId);
        Long totalMakeGathering = gatheringMemberQueryService.countByUserIdAndRole(userId, Role.HOST);
        Long totalTheme = themeVisitQueryService.countByUserId(userId);
        Long successCount = reviewQueryService.countBySuccess(true);
        Long failCount = reviewQueryService.countBySuccess(false);
        return userMapper.toUserRs(user, totalGathering, totalMakeGathering, totalTheme, successCount, failCount, List.of(""));
    }

    @Transactional
    public void updateUser(Long userId, MultipartFile image, String nickname, String comment, Boolean emailMark, Boolean genderMark) {
        User user = userQueryService.findById(userId);
        try {
            String imageUrl = image == null ? user.getProfileImage() : s3Service.uploadImage("user/" + UUID.randomUUID() + ".png", image);
            user.updateUserInfo(imageUrl, nickname, comment, emailMark, genderMark);
        } catch (IOException e) {
            throw new ImageUploadFailException("Image upload fail", e);
        }
        userQueryService.save(user);
    }

    public Page<List<UserVisitThemeRs>> getMyVisitTheme(Long userId, Integer limit, Integer offset) {
        List<UserVisitThemeQuery> userPlayingThemeList = userThemeQueryMapper.findUserVisitThemes(userId, limit, offset);
        Long totalCount = themeVisitQueryService.countByUserId(userId);
        List<UserVisitThemeRs> data = userPlayingThemeList.stream().map(userMapper::toUserVisitThemeRs).toList();
        return visitThemePageMapper.toPage(totalCount, offset, data);
    }

    public OtherUserRs getUserByNickname(String nickname) {
        User user = userQueryService.findByNickname(nickname);
        Long totalGathering = gatheringMemberQueryService.countByUserId(user.getId());
        Long totalMakeGathering = gatheringMemberQueryService.countByUserIdAndRole(user.getId(), Role.HOST);
        Long totalTheme = themeVisitQueryService.countByUserId(user.getId());
        Long successCount = reviewQueryService.countBySuccess(true);
        Long failCount = reviewQueryService.countBySuccess(false);
        return userMapper.toOtherUserRs(user, totalGathering, totalMakeGathering, totalTheme, successCount, failCount, List.of(""));
    }

    public Page<List<UserParticipatingGatheringRs>> getMyParticipatingGathering(Long userId, Integer limit, Integer offset) {
        List<UserParticipantGatheringQuery> userParticipantGatheringQuery = userGatheringQueryMapper.findUserParticipantGathering(userId, limit, offset);
        Set<Long> gatheringIdList = userParticipantGatheringQuery.stream().map(UserParticipantGatheringQuery::getGatheringId).collect(Collectors.toSet());
        Map<Long, List<GatheringMember>> collect = gatheringMemberQueryService.getMemberList(gatheringIdList).stream().collect(Collectors.groupingBy(gm -> gm.getGathering().getId()));
        List<UserParticipatingGatheringRs> data = userParticipantGatheringQuery.stream().map(userMapper::toUserParticipatingGatheringRs).toList();
        data.forEach(pg ->
                pg.setParticipants(
                        collect.get(pg.getGatheringId()).stream()
                                .map(gm -> UserParticipatingGatheringRs.ParticipatingUserDTO
                                        .builder()
                                        .name(gm.getUser().getNickname())
                                        .image(gm.getUser().getProfileImage())
                                        .build())
                                .toList()));
        Long totalCount = gatheringMemberQueryService.countByUserId(userId);
        return participatingGatheringPageMapper.toPage(totalCount, offset, data);
    }

    public Page<Map<LocalDate, List<UserCommentRs>>> getMyComment(Long userId, Pageable pageable) {
        List<GatheringComment> myComment = gatheringCommentQueryService.getMyComment(userId, pageable);
        Long totalCount = gatheringCommentQueryService.countMyComment(userId);
        LinkedHashMap<LocalDate, List<UserCommentRs>> data = myComment.stream()
                .map(userMapper::toUserCommentRs)
                .collect(Collectors.groupingBy(uc -> uc.getCreatedAt().toLocalDate(), LinkedHashMap::new, Collectors.toList()));
        return commentPageMapper.toPage(totalCount, pageable.getPageNumber(), data);
    }

    public Page<List<UserLikeGatheringRs>> getMyLikeGathering(Long userId, Integer limit, Integer offset) {
        List<UserLikeGatheringRs> data = userGatheringQueryMapper.findUserLikeGathering(userId, limit, offset).stream().map(userMapper::toUserLikeGatheringRs).toList();
        Long totalCount = gatheringLikeQueryService.countMyLikeGathering(userId);
        return likeGatheringPageMapper.toPage(totalCount, offset, data);
    }

    public Page<List<UserLikeThemeRs>> getMyLikeTheme(Long userId, Integer limit, Integer offset) {
        List<UserLikeThemeRs> data = userThemeQueryMapper.findUserLikeThemes(userId, limit, offset).stream().map(userMapper::toUserLikeThemeRs).toList();
        Long totalCount = themeLikeQueryService.countMyLikeTheme(userId);
        return likeThemePageMapper.toPage(totalCount, offset, data);
    }

    public Map<LocalDate, List<UserScheduleRs>> getMySchedule(Long userId) {
        List<UserScheduleQuery> userScheduleQuery = userGatheringQueryMapper.findUserSchedule(userId);
        return userScheduleQuery.stream()
                .collect(Collectors.groupingBy(gsv -> gsv.getDateTime().toLocalDate(),
                        Collectors.mapping(userMapper::toUserScheduleRs, Collectors.toList())));
    }
}
