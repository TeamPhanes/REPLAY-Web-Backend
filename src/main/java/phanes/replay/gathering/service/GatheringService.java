package phanes.replay.gathering.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import phanes.replay.common.dto.mapper.PageMapper;
import phanes.replay.common.dto.response.Page;
import phanes.replay.gathering.domain.Gathering;
import phanes.replay.gathering.domain.GatheringContent;
import phanes.replay.gathering.domain.GatheringLike;
import phanes.replay.gathering.domain.GatheringMember;
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

@Service
@RequiredArgsConstructor
public class GatheringService {

    private final GatheringContentQueryService gatheringContentQueryService;
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

    public GatheringDetailRs getGatheringDetail(Long gatheringId) {
        return gatheringMapper.toGatheringDetailRs(gatheringContentQueryService.findByGatheringIdWithGathering(gatheringId));
    }

    @Transactional
    public void createGathering(Long userId, GatheringCreateRq gatheringCreateRq) {
        User user = userQueryService.findByUserId(userId);
        Theme theme = themeQueryService.getTheme(gatheringCreateRq.getThemeId());
        ThemeContent themeContent = themeContentQueryService.findById(gatheringCreateRq.getThemeId());
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

    public void deleteGathering(Long userId, Long gatheringId) {
        GatheringMember hostGathering = gatheringMemberQueryService.findHostByUserIdAndGatheringId(userId, gatheringId);
        Gathering gathering = hostGathering.getGathering();
        GatheringContent gatheringContent = gatheringContentQueryService.findByGatheringId(gatheringId);
        gatheringQueryService.delete(gathering);
        gatheringContentQueryService.delete(gatheringContent);
    }

    public void updateGatheringLike(Long userId, Long gatheringId) {
        User user = userQueryService.findByUserId(userId);
        Gathering gathering = gatheringQueryService.findById(gatheringId);
        GatheringLike gatheringLike = GatheringLike.builder().user(user).gathering(gathering).build();
        gatheringLikeQueryService.save(gatheringLike);
    }

    public void deleteGatheringLike(Long userId, Long gatheringId) {
        GatheringLike gatheringLike = gatheringLikeQueryService.findByUserIdAndGatheringId(userId, gatheringId);
        gatheringLikeQueryService.delete(gatheringLike);
    }
}
