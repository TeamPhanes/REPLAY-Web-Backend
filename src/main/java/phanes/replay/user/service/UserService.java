package phanes.replay.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import phanes.replay.gathering.domain.enums.Role;
import phanes.replay.gathering.dto.MyCommentDto;
import phanes.replay.gathering.dto.response.Participant;
import phanes.replay.gathering.repository.GatheringCommentJooqRepository;
import phanes.replay.gathering.repository.GatheringMemberJooqRepository;
import phanes.replay.review.repository.ReviewImageJooqRepository;
import phanes.replay.theme.repository.GenreJooqRepository;
import phanes.replay.theme.repository.ThemeVisitJooqRepository;
import phanes.replay.user.domain.User;
import phanes.replay.user.dto.user.*;
import phanes.replay.user.mapper.UserMapper;
import phanes.replay.user.repository.AchievementJooqRepository;
import phanes.replay.user.repository.UserJooqRepository;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserQueryService userQueryService;
    private final GatheringMemberJooqRepository gatheringMemberJooqRepository;
    private final GatheringCommentJooqRepository gatheringCommentJooqRepository;
    private final ThemeVisitJooqRepository themeVisitJooqRepository;
    private final AchievementJooqRepository achievementJooqRepository;
    private final UserJooqRepository userJooqRepository;
    private final GenreJooqRepository genreJooqRepository;
    private final ReviewImageJooqRepository reviewImageJooqRepository;
    private final UserMapper userMapper;

    public UserRs findByUserId(Long userId) {
        return userMapper.toUserRs(userQueryService.findById(userId));
    }

    public ProfileRs findProfileById(Long userId, boolean isOwner) {
        User user = userQueryService.findById(userId);
        List<Role> participantGatheringList = gatheringMemberJooqRepository.findParticipantAllByUserId(userId);
        Integer visitGatheringCount = participantGatheringList.size();
        Integer createGatheringCount = participantGatheringList.stream().filter(r -> r.equals(Role.HOST)).toList().size();
        List<Boolean> visitThemeList = themeVisitJooqRepository.findVisitByUserId(userId);
        Integer visitThemeCount = visitThemeList.size();
        Integer successThemeCount = visitThemeList.stream().filter(r -> r).toList().size();
        List<AchievementDto> achievementList = achievementJooqRepository.findByUserIdAndOwner(userId, isOwner);
        return userMapper.toProfileRs(user, createGatheringCount, visitGatheringCount, visitThemeCount, successThemeCount, achievementList);
    }

    public List<MyCommentRs> findCommentById(Long userId, Pageable pageable) {
        User user = userQueryService.findById(userId);
        List<MyCommentDto> myCommentList = gatheringCommentJooqRepository.findAllByUserId(userId, pageable);
        return myCommentList.stream().map(c -> userMapper.toMyCommentRs(user, c)).toList();
    }

    public Page<MyVisitThemeRs> findVisitThemeById(Long userId, Pageable pageable) {
        Page<MyVisitThemeDto> visitThemeList = userJooqRepository.findVisitThemeById(userId, pageable);
        List<Long> themeIdList = visitThemeList.stream().map(MyVisitThemeDto::getId).toList();
        List<Long> reviewIdList = visitThemeList.stream().map(MyVisitThemeDto::getReviewId).toList();
        Map<Long, List<String>> genreListMap = genreJooqRepository.findAllByThemeIdList(themeIdList);
        Map<Long, List<String>> reviewImageListMap = reviewImageJooqRepository.findAllByReviewIdList(reviewIdList);
        List<MyVisitThemeRs> contents = visitThemeList.stream()
                .map(t -> userMapper.toMyVisitThemeRs(t,
                        genreListMap.getOrDefault(t.getId(), Collections.emptyList()),
                        reviewImageListMap.getOrDefault(t.getReviewId(), Collections.emptyList())
                )).toList();
        return new PageImpl<>(contents, pageable, visitThemeList.getTotalElements());
    }

    public Page<MyParticipantGatheringRs> findParticipantGatheringById(Long userId, Pageable pageable) {
        Page<MyParticipantGatheringDto> participantGatheringList = userJooqRepository.findParticipantGatheringById(userId, pageable);
        List<Long> themeIdList = participantGatheringList.stream().map(MyParticipantGatheringDto::getThemeId).toList();
        List<Long> gatheringIdList = participantGatheringList.stream().map(MyParticipantGatheringDto::getId).toList();
        Map<Long, List<String>> genreListMap = genreJooqRepository.findAllByThemeIdList(themeIdList);
        Map<Long, List<Participant>> participantListMap = gatheringMemberJooqRepository.findAllByGatheringIdList(gatheringIdList);
        List<MyParticipantGatheringRs> contents = participantGatheringList.stream()
                .map(g ->
                        userMapper.toMyParticipantGatheringRs(
                                g,
                                genreListMap.getOrDefault(g.getThemeId(), Collections.emptyList()),
                                participantListMap.getOrDefault(g.getId(), Collections.emptyList())
                        )
                ).toList();
        return new PageImpl<>(contents, pageable, participantGatheringList.getTotalElements());
    }
}