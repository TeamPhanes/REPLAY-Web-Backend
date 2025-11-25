package phanes.replay.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import phanes.replay.gathering.domain.enums.Role;
import phanes.replay.gathering.dto.MyCommentDto;
import phanes.replay.gathering.repository.GatheringCommentJooqRepository;
import phanes.replay.gathering.repository.GatheringMemberJooqRepository;
import phanes.replay.theme.repository.ThemeVisitJooqRepository;
import phanes.replay.user.domain.User;
import phanes.replay.user.dto.user.AchievementDto;
import phanes.replay.user.dto.user.MyCommentRs;
import phanes.replay.user.dto.user.ProfileRs;
import phanes.replay.user.dto.user.UserRs;
import phanes.replay.user.mapper.UserMapper;
import phanes.replay.user.repository.AchievementJooqRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserQueryService userQueryService;
    private final GatheringMemberJooqRepository gatheringMemberJooqRepository;
    private final GatheringCommentJooqRepository gatheringCommentJooqRepository;
    private final ThemeVisitJooqRepository themeVisitJooqRepository;
    private final AchievementJooqRepository achievementJooqRepository;
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
}