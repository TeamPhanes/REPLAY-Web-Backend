package phanes.replay.gathering.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import phanes.replay.common.dto.mapper.PageMapper;
import phanes.replay.common.dto.response.Page;
import phanes.replay.exception.HostNotFoundException;
import phanes.replay.exception.IllegalAccessException;
import phanes.replay.gathering.domain.*;
import phanes.replay.gathering.domain.enums.Role;
import phanes.replay.gathering.dto.mapper.GatheringMapper;
import phanes.replay.gathering.dto.request.GatheringCreateRq;
import phanes.replay.gathering.dto.request.GatheringUpdateRq;
import phanes.replay.gathering.dto.response.GatheringDetailRs;
import phanes.replay.gathering.dto.response.GatheringRs;
import phanes.replay.gathering.persistence.mapper.GatheringQueryMapper;
import phanes.replay.theme.domain.Theme;
import phanes.replay.theme.domain.ThemeContent;
import phanes.replay.theme.service.ThemeContentQueryService;
import phanes.replay.theme.service.ThemeQueryService;
import phanes.replay.user.domain.User;
import phanes.replay.user.service.UserQueryService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GatheringService {

    private final GatheringContentQueryService gatheringContentQueryService;
    private final GatheringCommentQueryService gatheringCommentQueryService;
    private final GatheringMemberQueryService gatheringMemberQueryService;
    private final GatheringLikeQueryService gatheringLikeQueryService;
    private final ThemeContentQueryService themeContentQueryService;
    private final GatheringQueryService gatheringQueryService;
    private final GatheringQueryMapper gatheringQueryMapper;
    private final PageMapper<List<GatheringRs>> pageMapper;
    private final ThemeQueryService themeQueryService;
    private final UserQueryService userQueryService;
    private final GatheringMapper gatheringMapper;

    public Page<List<GatheringRs>> getGatheringList(Long userId, String sortBy, String keyword, String city, String state, LocalDateTime startDate, LocalDateTime endDate, String genre, Integer limit, Integer offset) {
        Long totalCount = gatheringQueryMapper.countByKeywordAndAddress(sortBy, keyword, city, state, startDate, endDate, genre);
        List<GatheringRs> data = gatheringQueryMapper.findAllByKeywordAndAddress(userId, sortBy, keyword, city, state, startDate, endDate, genre, limit, offset).stream()
                .map(gatheringMapper::toGatheringRs).toList();
        return pageMapper.toPage(totalCount, offset, data);
    }

    public Page<List<GatheringRs>> getGatheringHostList(String hostName) {
        User user = userQueryService.findByNickname(hostName);
        Long totalCount = gatheringMemberQueryService.countByUserIdAndRoleEquals(user.getId(), Role.HOST);
        List<GatheringRs> data = gatheringQueryMapper.findAllByHost(user.getId())
                .stream()
                .map(gatheringMapper::toGatheringRs)
                .toList();
        return pageMapper.toPage(totalCount, 0, data);
    }

    public GatheringDetailRs getGatheringDetail(Long gatheringId) {
        return gatheringMapper.toGatheringDetailRs(gatheringContentQueryService.findByGatheringIdWithGathering(gatheringId));
    }

    @Transactional
    public void createGathering(Long userId, GatheringCreateRq gatheringCreateRq) {
        User user = userQueryService.findById(userId);
        Theme theme = themeQueryService.findById(gatheringCreateRq.getThemeId());
        ThemeContent themeContent = themeContentQueryService.findByThemeId(gatheringCreateRq.getThemeId());
        Gathering gathering = Gathering.builder()
                .theme(theme)
                .name(gatheringCreateRq.getName())
                .image(theme.getImage())
                .capacity(gatheringCreateRq.getCapacity())
                .dateTime(gatheringCreateRq.getDateTime())
                .registrationStart(gatheringCreateRq.getRegistrationStart())
                .registrationEnd(gatheringCreateRq.getRegistrationEnd())
                .build();
        GatheringContent gatheringContent = GatheringContent.builder()
                .gathering(gathering)
                .content(gatheringCreateRq.getContent())
                .price(gatheringCreateRq.getPrice())
                .isIndividual(gatheringCreateRq.getIsIndividual())
                .image(themeContent.getImage())
                .build();
        GatheringMember gatheringMember = GatheringMember.builder()
                .user(user)
                .gathering(gathering)
                .role(Role.HOST)
                .build();
        gatheringQueryService.save(gathering);
        gatheringContentQueryService.save(gatheringContent);
        gatheringMemberQueryService.save(gatheringMember);
    }

    public void updateGathering(Long userId, Long gatheringId, GatheringUpdateRq gatheringUpdateRq) {
        GatheringMember hostGathering = gatheringMemberQueryService.findHostByUserIdAndGatheringId(userId, gatheringId);
        Gathering gathering = hostGathering.getGathering();
        GatheringContent gatheringContent = gatheringContentQueryService.findByGatheringId(gatheringId);
        gathering.updateGathering(gatheringUpdateRq);
        gatheringContent.updateGatheringContent(gatheringUpdateRq);
        gatheringQueryService.save(gathering);
        gatheringContentQueryService.save(gatheringContent);
    }

    public void updateGatheringLike(Long userId, Long gatheringId) {
        User user = userQueryService.findById(userId);
        Gathering gathering = gatheringQueryService.findById(gatheringId);
        GatheringLike gatheringLike = GatheringLike.builder().user(user).gathering(gathering).build();
        gatheringLikeQueryService.save(gatheringLike);
    }

    @Transactional
    public void deleteGathering(Long userId, Long gatheringId) {
        List<GatheringMember> gatheringMemberList = gatheringMemberQueryService.findAllByGatheringIdWithUserAndGathering(gatheringId);
        GatheringMember host = findHost(gatheringMemberList);
        if (!Objects.equals(userId, host.getUser().getId())) {
            throw new IllegalAccessException("Only the host can delete a gathering");
        }
        GatheringContent gatheringContent = gatheringContentQueryService.findByGatheringId(gatheringId);
        List<GatheringLike> gatheringLikeList = gatheringLikeQueryService.findAllByGatheringId(gatheringId);
        List<GatheringComment> gatheringCommentList = gatheringCommentQueryService.findAllByGatheringId(gatheringId);
        gatheringCommentQueryService.deleteAll(gatheringCommentList);
        gatheringLikeQueryService.deleteAll(gatheringLikeList);
        gatheringMemberQueryService.deleteAll(gatheringMemberList);
        gatheringContentQueryService.delete(gatheringContent);
        gatheringQueryService.delete(host.getGathering());
    }

    private GatheringMember findHost(List<GatheringMember> gatheringMemberList) {
        return gatheringMemberList.stream().filter(gm -> gm.getRole().equals(Role.HOST)).findFirst().orElseThrow(() -> new HostNotFoundException("Host not found"));
    }

    public void deleteGatheringLike(Long userId, Long gatheringId) {
        GatheringLike gatheringLike = gatheringLikeQueryService.findByUserIdAndGatheringId(userId, gatheringId);
        gatheringLikeQueryService.delete(gatheringLike);
    }
}
