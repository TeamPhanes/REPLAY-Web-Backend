package phanes.replay.gathering.service;

import lombok.RequiredArgsConstructor;
import org.opensearch.client.opensearch.core.SearchResponse;
import org.opensearch.client.opensearch.core.search.Hit;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import phanes.replay.gathering.domain.*;
import phanes.replay.gathering.domain.enums.Role;
import phanes.replay.gathering.dto.GatheringCommentDto;
import phanes.replay.gathering.dto.GatheringDetailDto;
import phanes.replay.gathering.dto.GatheringDto;
import phanes.replay.gathering.dto.event.GatheringCreatedEvent;
import phanes.replay.gathering.dto.request.GatheringRq;
import phanes.replay.gathering.dto.request.GatheringUpdateRq;
import phanes.replay.gathering.dto.response.*;
import phanes.replay.gathering.mapper.GatheringMapper;
import phanes.replay.gathering.repository.GatheringCommentJooqRepository;
import phanes.replay.gathering.repository.GatheringJooqRepository;
import phanes.replay.gathering.repository.GatheringLikeJooqRepository;
import phanes.replay.gathering.repository.GatheringMemberJooqRepository;
import phanes.replay.opensearch.domain.GatheringDoc;
import phanes.replay.opensearch.dto.response.Cursor;
import phanes.replay.opensearch.dto.response.SearchPage;
import phanes.replay.opensearch.repository.OpenSearchRepository;
import phanes.replay.theme.domain.Theme;
import phanes.replay.theme.repository.GenreJooqRepository;
import phanes.replay.theme.repository.ThemeJooqRepository;
import phanes.replay.theme.service.ThemeQueryService;
import phanes.replay.user.domain.User;
import phanes.replay.user.service.UserQueryService;
import phanes.replay.utils.CursorUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GatheringService {

    public static final int MAX_CAPACITY = 6;
    private final UserQueryService userQueryService;
    private final ThemeQueryService themeQueryService;
    private final GatheringQueryService gatheringQueryService;
    private final GatheringMemberQueryService gatheringMemberQueryService;
    private final GatheringContentQueryService gatheringContentQueryService;
    private final GatheringLikeQueryService gatheringLikeQueryService;
    private final GatheringCommentQueryService gatheringCommentQueryService;
    private final GatheringJooqRepository gatheringJooqRepository;
    private final GatheringMemberJooqRepository gatheringMemberJooqRepository;
    private final GatheringCommentJooqRepository gatheringCommentJooqRepository;
    private final GatheringLikeJooqRepository gatheringLikeJooqRepository;
    private final ThemeJooqRepository themeJooqRepository;
    private final GenreJooqRepository genreJooqRepository;
    private final OpenSearchRepository openSearchRepository;
    private final GatheringMapper gatheringMapper;
    private final ApplicationEventPublisher eventPublisher;

    public Page<GatheringRs> findAll(Long userId, Long themeId, Pageable pageable, List<String> locations, List<String> genres) {
        Page<GatheringDto> gatheringDtoList = gatheringJooqRepository.findAll(userId, themeId, pageable, locations, genres);
        List<Long> themeIdList = themeId == null ? gatheringDtoList.getContent().stream().map(GatheringDto::getThemeId).toList() : List.of(themeId);
        Map<Long, List<String>> genreListMap = genreJooqRepository.findAllByThemeIdList(themeIdList);
        List<GatheringRs> contents = gatheringDtoList.getContent().stream().map(g -> gatheringMapper.toGatheringRs(g, genreListMap.getOrDefault(g.getThemeId(), Collections.emptyList()))).toList();
        return new PageImpl<>(contents, pageable, gatheringDtoList.getTotalElements());
    }

    public SearchPage<GatheringSearchRs> findAllByLocationAndGenreAndKeyword(Long userId, Integer size, Cursor cursor, List<String> locations, List<String> genres, String keyword) {
        SearchResponse<GatheringDoc> response = openSearchRepository.findAllGatheringByLocationAndGenreAndKeyword(size, cursor, locations, genres, keyword);
        List<Hit<GatheringDoc>> hits = response.hits().hits();
        List<Long> gatheringIdList = hits.stream().map(t -> Objects.requireNonNull(t.source()).getId()).toList();
        Map<Long, GatheringDto> gatheringMap = gatheringJooqRepository.findByIdList(userId, gatheringIdList);
        List<GatheringSearchRs> contents = hits.stream().map(Hit::source)
                .map(g ->
                        gatheringMapper.toGatheringSearchRs(
                                g,
                                gatheringMap.getOrDefault(Objects.requireNonNull(g).getId(), null)
                        )).toList();
        Cursor nextCursor = CursorUtils.getNextCursor(hits, size);
        return new SearchPage<>(contents, nextCursor);
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

    @Transactional
    public void updateGathering(Long userId, Long gatheringId, GatheringUpdateRq gatheringUpdateRq) {
        Gathering gathering = gatheringQueryService.findById(gatheringId);
        GatheringMember member = gatheringMemberQueryService.findByUserId(userId, gatheringId);
        if (!member.getRole().equals(Role.HOST)) {
            throw new RuntimeException("모임은 Host만 수정할 수 있습니다.");
        }
        if (gatheringUpdateRq.getDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("날짜는 현재 시간보다 이전일 수 없습니다.");
        }
        if (gatheringUpdateRq.getRegistrationEnd().isAfter(gatheringUpdateRq.getDate())) {
            throw new IllegalArgumentException("모집 마감일은 모집일보다 이후일 수 없습니다.");
        }
        if (gatheringUpdateRq.getRegistrationStart().isAfter(gatheringUpdateRq.getRegistrationEnd())) {
            throw new IllegalArgumentException("모집 시작일은 모집 마감일보다 이후일 수 없습니다.");
        }
        if (gatheringUpdateRq.getCapacity() > MAX_CAPACITY) {
            throw new IllegalArgumentException(String.format("모임 인원은 %d명을 초과할 수 없습니다.", MAX_CAPACITY));
        }
        gathering.update(gatheringUpdateRq);
        gatheringQueryService.save(gathering);

        GatheringContent gatheringContent = gatheringContentQueryService.findByGatheringId(gathering.getId());
        gatheringContent.update(gatheringUpdateRq);
    }

    @Transactional
    public void deleteGathering(Long userId, Long gatheringId) {
        Gathering gathering = gatheringQueryService.findById(gatheringId);
        List<GatheringMember> gatheringMemberList = gatheringMemberQueryService.findAllByGatheringId(gathering.getId());
        if(!isHost(userId, gatheringMemberList)) {
            throw new IllegalArgumentException("모임 삭제는 Host만 가능합니다.");
        }
        GatheringContent gatheringContent = gatheringContentQueryService.findByGatheringId(gathering.getId());
        List<GatheringLike> gatheringLikeList = gatheringLikeQueryService.findAllByGatheringId(gathering.getId());
        List<GatheringComment> gatheringCommentList = gatheringCommentQueryService.findAllByGatheringId(gathering.getId());

        gatheringCommentQueryService.deleteAll(gatheringCommentList);
        gatheringLikeQueryService.deleteAll(gatheringLikeList);
        gatheringMemberQueryService.deleteAll(gatheringMemberList);
        gatheringContentQueryService.delete(gatheringContent);
        gatheringQueryService.delete(gathering);
    }

    private Boolean isHost(Long userId, List<GatheringMember> gatheringMemberList) {
        for (GatheringMember gm : gatheringMemberList) {
            if (Objects.equals(gm.getUser().getId(), userId) && gm.getRole().equals(Role.HOST)) {
                return true;
            }
        }
        return false;
    }
}