package phanes.replay.gathering.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import phanes.replay.gathering.domain.Gathering;
import phanes.replay.gathering.domain.GatheringContent;
import phanes.replay.gathering.domain.GatheringLike;
import phanes.replay.gathering.domain.GatheringMember;
import phanes.replay.gathering.domain.enums.Role;
import phanes.replay.gathering.dto.GatheringCommentDto;
import phanes.replay.gathering.dto.GatheringDetailDto;
import phanes.replay.gathering.dto.GatheringDto;
import phanes.replay.gathering.dto.event.GatheringCreatedEvent;
import phanes.replay.gathering.dto.request.GatheringRq;
import phanes.replay.gathering.dto.response.GatheringCommentRs;
import phanes.replay.gathering.dto.response.GatheringDetailRs;
import phanes.replay.gathering.dto.response.GatheringRs;
import phanes.replay.gathering.dto.response.Participant;
import phanes.replay.gathering.mapper.GatheringMapper;
import phanes.replay.gathering.repository.GatheringCommentJooqRepository;
import phanes.replay.gathering.repository.GatheringJooqRepository;
import phanes.replay.gathering.repository.GatheringLikeJooqRepository;
import phanes.replay.gathering.repository.GatheringMemberJooqRepository;
import phanes.replay.theme.domain.Theme;
import phanes.replay.theme.repository.GenreJooqRepository;
import phanes.replay.theme.repository.ThemeJooqRepository;
import phanes.replay.theme.service.ThemeQueryService;
import phanes.replay.user.domain.User;
import phanes.replay.user.service.UserQueryService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GatheringService {

    private final UserQueryService userQueryService;
    private final ThemeQueryService themeQueryService;
    private final GatheringQueryService gatheringQueryService;
    private final GatheringMemberQueryService gatheringMemberQueryService;
    private final GatheringContentQueryService gatheringContentQueryService;
    private final GatheringLikeQueryService gatheringLikeQueryService;
    private final GatheringJooqRepository gatheringJooqRepository;
    private final GatheringMemberJooqRepository gatheringMemberJooqRepository;
    private final GatheringCommentJooqRepository gatheringCommentJooqRepository;
    private final GatheringLikeJooqRepository gatheringLikeJooqRepository;
    private final GenreJooqRepository genreJooqRepository;
    private final GatheringMapper gatheringMapper;
    private final ApplicationEventPublisher eventPublisher;
    private final ThemeJooqRepository themeJooqRepository;

    public Page<GatheringRs> findAll(Long userId, Long themeId, Pageable pageable, List<String> locations, List<String> genres) {
        Page<GatheringDto> gatheringDtoList = gatheringJooqRepository.findAll(userId, themeId, pageable, locations, genres);
        List<Long> themeIdList = themeId == null ? gatheringDtoList.getContent().stream().map(GatheringDto::getThemeId).toList() : List.of(themeId);
        Map<Long, List<String>> genreListMap = genreJooqRepository.findAllByThemeIdList(themeIdList);
        List<GatheringRs> contents = gatheringDtoList.getContent().stream().map(g -> gatheringMapper.toGatheringRs(g, genreListMap.getOrDefault(g.getThemeId(), Collections.emptyList()))).toList();
        return new PageImpl<>(contents, pageable, gatheringDtoList.getTotalElements());
    }

    public GatheringDetailRs findById(Long userId, Long gatheringId) {
        GatheringDetailDto gatheringDetailDto = gatheringJooqRepository.findById(userId, gatheringId);
        List<String> genres = genreJooqRepository.findByThemeId(gatheringDetailDto.getThemeId());
        List<Participant> participants = gatheringMemberJooqRepository.findAllByGatheringId(gatheringId);
        return gatheringMapper.toGatheringDetailRs(gatheringDetailDto, genres, participants, participants.size());
    }

    public void saveGatheringLike(Long userId, Long gatheringId) {
        User user = userQueryService.findById(userId);
        Gathering gathering = gatheringQueryService.findById(gatheringId);
        GatheringLike gatheringLike = GatheringLike.builder()
                .gathering(gathering)
                .user(user)
                .build();
        gatheringLikeQueryService.save(gatheringLike);
    }

    public void deleteGatheringLike(Long userId, Long gatheringId) {
        GatheringLike gatheringLike = gatheringLikeQueryService.findByUserIdAndGatheringId(userId, gatheringId);
        gatheringLikeQueryService.delete(gatheringLike);
    }

    public Page<GatheringRs> findByDateBetween(Long userId, Pageable pageable, LocalDateTime date) {
        Page<GatheringDto> gatheringDtoList = gatheringJooqRepository.findAllByDate(userId, pageable, date);
        List<Long> themeIdList = gatheringDtoList.getContent().stream().map(GatheringDto::getThemeId).toList();
        Map<Long, List<String>> genreListMap = genreJooqRepository.findAllByThemeIdList(themeIdList);
        List<GatheringRs> contents = gatheringDtoList.getContent().stream().map(g -> gatheringMapper.toGatheringRs(g, genreListMap.getOrDefault(g.getThemeId(), Collections.emptyList()))).toList();
        return new PageImpl<>(contents, pageable, gatheringDtoList.getTotalElements());
    }

    public Page<GatheringCommentRs> findCommentAll(Pageable pageable, Long gatheringId) {
        Page<GatheringCommentDto> commentList = gatheringCommentJooqRepository.findParentCommentByGatheringId(pageable, gatheringId);
        List<Long> commentIdList = commentList.stream().map(GatheringCommentDto::getId).toList();
        Map<Long, List<GatheringCommentDto>> childCommentList = gatheringCommentJooqRepository.findChileCommentById(commentIdList);
        List<GatheringCommentRs> contents = commentList.stream()
                .map(gatheringMapper::toGatheringCommentRs)
                .toList();
        contents.forEach(g -> g.setComments(
                childCommentList.getOrDefault(g.getId(), Collections.emptyList()).stream()
                        .map(gatheringMapper::toGatheringCommentRs)
                        .toList())
        );
        return new PageImpl<>(contents, pageable, commentList.getTotalElements());
    }

    public Page<GatheringRs> findAllByLike(Long userId, Pageable pageable, List<String> locations, List<String> genres) {
        Page<GatheringDto> gatheringDtoList = gatheringLikeJooqRepository.findAllByLike(userId, pageable, locations, genres);
        List<Long> themeIdList = gatheringDtoList.getContent().stream().map(GatheringDto::getThemeId).toList();
        Map<Long, List<String>> genreListMap = genreJooqRepository.findAllByThemeIdList(themeIdList);
        List<GatheringRs> contents = gatheringDtoList.getContent().stream().map(g -> gatheringMapper.toGatheringRs(g, genreListMap.getOrDefault(g.getThemeId(), Collections.emptyList()))).toList();
        return new PageImpl<>(contents, pageable, gatheringDtoList.getTotalElements());
    }

    @Transactional
    public void saveGathering(Long userId, GatheringRq gatheringRq) {
        User user = userQueryService.findById(userId);
        Theme theme = themeQueryService.findById(gatheringRq.getThemeId());
        Gathering gathering = Gathering.builder()
                .theme(theme)
                .name(gatheringRq.getName())
                .capacity(gatheringRq.getCapacity())
                .date(gatheringRq.getDate())
                .registrationStart(gatheringRq.getRegistrationStart())
                .registrationEnd(gatheringRq.getRegistrationEnd())
                .build();
        Gathering savedGathering = gatheringQueryService.save(gathering);
        GatheringMember gatheringMember = GatheringMember.builder()
                .user(user)
                .gathering(gathering)
                .role(Role.HOST)
                .build();
        gatheringMemberQueryService.save(gatheringMember);
        GatheringContent gatheringContent = GatheringContent.builder()
                .gathering(gathering)
                .content(gatheringRq.getContent())
                .isIndividual(gatheringRq.getIsIndividual())
                .price(gatheringRq.getPrice())
                .build();
        gatheringContentQueryService.save(gatheringContent);

        List<String> genres = genreJooqRepository.findByThemeId(theme.getId());
        String address = themeJooqRepository.findAddressById(theme.getId());
        GatheringCreatedEvent event = GatheringCreatedEvent.builder()
                .id(savedGathering.getId())
                .name(savedGathering.getName())
                .date(savedGathering.getDate())
                .themeId(theme.getId())
                .title(theme.getTitle())
                .image(theme.getImage())
                .playtime(theme.getPlaytime())
                .level(theme.getLevel())
                .genres(genres)
                .address(address)
                .build();
        eventPublisher.publishEvent(event);
    }
}