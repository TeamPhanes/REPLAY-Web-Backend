package phanes.replay.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import phanes.replay.common.dto.mapper.PageMapper;
import phanes.replay.common.dto.response.Page;
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

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PageMapper<List<UserParticipatingGatheringRs>> participatingGatheringPageMapper;
    private final PageMapper<Map<LocalDate, List<UserCommentRs>>> commentPageMapper;
    private final PageMapper<List<UserLikeGatheringRs>> likeGatheringPageMapper;
    private final GatheringCommentQueryService gatheringCommentQueryService;
    private final GatheringMemberQueryService gatheringMemberQueryService;
    private final PageMapper<List<UserVisitThemeRs>> visitThemePageMapper;
    private final PageMapper<List<UserLikeThemeRs>> likeThemePageMapper;
    private final GatheringLikeQueryService gatheringLikeQueryService;
    private final UserGatheringQueryMapper userGatheringQueryMapper;
    private final ThemeVisitQueryService themeVisitQueryService;
    private final ThemeLikeQueryService themeLikeQueryService;
    private final UserThemeQueryMapper userThemeQueryMapper;
    private final ReviewQueryService reviewQueryService;
    private final UserQueryService userQueryService;
    private final UserMapper userMapper;
    private final S3Service s3Service;

    public UserRs findByUserId(Long userId) {
        User user = userQueryService.findById(userId);
        Long totalGathering = gatheringMemberQueryService.countByUserId(userId);
        Long totalMakeGathering = gatheringMemberQueryService.countByUserIdAndRoleEquals(userId, Role.HOST);
        Long totalTheme = themeVisitQueryService.countByUserId(userId);
        Long successCount = reviewQueryService.countByUserIdAndSuccess(userId, true);
        Long failCount = reviewQueryService.countByUserIdAndSuccess(userId, false);
        return userMapper.toUserRs(user, totalGathering, totalMakeGathering, totalTheme, successCount, failCount, List.of(""));
    }

    public OtherUserRs findByNickname(String nickname) {
        User user = userQueryService.findByNickname(nickname);
        Long totalGathering = gatheringMemberQueryService.countByUserId(user.getId());
        Long totalMakeGathering = gatheringMemberQueryService.countByUserIdAndRoleEquals(user.getId(), Role.HOST);
        Long totalTheme = themeVisitQueryService.countByUserId(user.getId());
        Long successCount = reviewQueryService.countByUserIdAndSuccess(user.getId(), true);
        Long failCount = reviewQueryService.countByUserIdAndSuccess(user.getId(), false);
        return userMapper.toOtherUserRs(user, totalGathering, totalMakeGathering, totalTheme, successCount, failCount, List.of(""));
    }

    @Transactional
    public void updateUser(Long userId, MultipartFile image, String nickname, String comment, Boolean emailMark, Boolean genderMark) {
        User user = userQueryService.findById(userId);
        String imageUrl = image == null ? user.getProfileImage() : s3Service.uploadImage("user/" + UUID.randomUUID() + ".png", image);
        user.updateUserInfo(imageUrl, nickname, comment, emailMark, genderMark);
        userQueryService.save(user);
    }

    public Page<List<UserVisitThemeRs>> findVisitThemeListById(Long userId, Integer limit, Integer offset) {
        List<UserVisitThemeQuery> userPlayingThemeList = userThemeQueryMapper.findVisitThemeListById(userId, limit, offset);
        Long totalCount = themeVisitQueryService.countByUserId(userId);
        List<UserVisitThemeRs> data = userPlayingThemeList
                .stream()
                .map(userMapper::toUserVisitThemeRs)
                .toList();
        return visitThemePageMapper.toPage(totalCount, offset, data);
    }

    public Page<List<UserParticipatingGatheringRs>> findParticipatingGatheringListById(Long userId, Integer limit, Integer offset) {
        List<UserParticipantGatheringQuery> userParticipantGatheringQuery = userGatheringQueryMapper.findUserParticipantGatheringById(userId, limit, offset);
        Set<Long> gatheringIdList = userParticipantGatheringQuery
                .stream()
                .map(UserParticipantGatheringQuery::getGatheringId)
                .collect(Collectors.toSet());
        Map<Long, List<GatheringMember>> collect = gatheringMemberQueryService.findAllByGatheringIdIn(gatheringIdList)
                .stream()
                .collect(Collectors.groupingBy(gm -> gm.getGathering().getId()));
        List<UserParticipatingGatheringRs> data = userParticipantGatheringQuery
                .stream()
                .map(userMapper::toUserParticipatingGatheringRs)
                .toList();
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

    public Page<Map<LocalDate, List<UserCommentRs>>> findCommentListById(Long userId, Pageable pageable) {
        List<GatheringComment> myComment = gatheringCommentQueryService.findAllByUserId(userId, pageable);
        Long totalCount = gatheringCommentQueryService.countByUserId(userId);
        LinkedHashMap<LocalDate, List<UserCommentRs>> data = myComment.stream()
                .map(userMapper::toUserCommentRs)
                .collect(Collectors.groupingBy(uc -> uc.getCreatedAt().toLocalDate(), LinkedHashMap::new, Collectors.toList()));
        return commentPageMapper.toPage(totalCount, pageable.getPageNumber(), data);
    }

    public Page<List<UserLikeGatheringRs>> findLikeGatheringListById(Long userId, Integer limit, Integer offset) {
        List<UserLikeGatheringRs> data = userGatheringQueryMapper.findUserLikeGatheringById(userId, limit, offset)
                .stream()
                .map(userMapper::toUserLikeGatheringRs)
                .toList();
        Long totalCount = gatheringLikeQueryService.countByUserId(userId);
        return likeGatheringPageMapper.toPage(totalCount, offset, data);
    }

    public Page<List<UserLikeThemeRs>> findLikeThemeListById(Long userId, Integer limit, Integer offset) {
        List<UserLikeThemeRs> data = userThemeQueryMapper.findUserLikeThemeById(userId, limit, offset)
                .stream()
                .map(userMapper::toUserLikeThemeRs)
                .toList();
        Long totalCount = themeLikeQueryService.countByUserId(userId);
        return likeThemePageMapper.toPage(totalCount, offset, data);
    }

    public Map<LocalDate, List<UserScheduleRs>> findScheduleByUserId(Long userId) {
        List<UserScheduleQuery> userScheduleQuery = userGatheringQueryMapper.findScheduleById(userId);
        return userScheduleQuery.stream()
                .collect(Collectors.groupingBy(gsv -> gsv.getDateTime().toLocalDate(), Collectors.mapping(userMapper::toUserScheduleRs, Collectors.toList())));
    }
}
