package phanes.replay.gathering.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import phanes.replay.gathering.domain.Gathering;
import phanes.replay.gathering.domain.GatheringLike;
import phanes.replay.gathering.dto.GatheringCommentDto;
import phanes.replay.gathering.dto.GatheringDetailDto;
import phanes.replay.gathering.dto.GatheringDto;
import phanes.replay.gathering.dto.response.GatheringCommentRs;
import phanes.replay.gathering.dto.response.GatheringDetailRs;
import phanes.replay.gathering.dto.response.GatheringRs;
import phanes.replay.gathering.dto.response.Participant;
import phanes.replay.gathering.mapper.GatheringMapper;
import phanes.replay.gathering.repository.GatheringCommentJooqRepository;
import phanes.replay.gathering.repository.GatheringJooqRepository;
import phanes.replay.gathering.repository.GatheringMemberJooqRepository;
import phanes.replay.theme.repository.GenreJooqRepository;
import phanes.replay.user.domain.User;
import phanes.replay.user.service.UserQueryService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GatheringService {

    private final UserQueryService userQueryService;
    private final GatheringQueryService gatheringQueryService;
    private final GatheringLikeQueryService gatheringLikeQueryService;
    private final GatheringJooqRepository gatheringJooqRepository;
    private final GatheringMemberJooqRepository gatheringMemberJooqRepository;
    private final GatheringCommentJooqRepository gatheringCommentJooqRepository;
    private final GenreJooqRepository genreJooqRepository;
    private final GatheringMapper gatheringMapper;

    public Page<GatheringRs> findAll(Long userId, Pageable pageable, List<String> locations, List<String> genres) {
        Page<GatheringDto> gatheringDtoList = gatheringJooqRepository.findAll(userId, pageable, locations, genres);
        List<Long> themeIdList = gatheringDtoList.getContent().stream().map(GatheringDto::getThemeId).toList();
        Map<Long, List<String>> genreListMap = genreJooqRepository.findAllByThemeIdList(themeIdList);
        List<GatheringRs> contents = gatheringDtoList.getContent().stream().map(g -> gatheringMapper.toGatheringRs(g, genreListMap.getOrDefault(g.getThemeId(), Collections.emptyList()))).toList();
        return new PageImpl<>(contents, pageable, gatheringDtoList.getTotalElements());
    }

    public GatheringDetailRs findById(Long userId, Long gatheringId) {
        GatheringDetailDto gatheringDetailDto = gatheringJooqRepository.findById(userId, gatheringId);
        List<String> genres = genreJooqRepository.findByThemeId(gatheringDetailDto.getThemeId());
        List<Participant> participants = gatheringMemberJooqRepository.findAllByGatheringId(gatheringId);
        List<GatheringCommentDto> commentList = gatheringCommentJooqRepository.findAllByGatheringId(gatheringId);
        Map<Long, List<GatheringCommentDto>> groupedByParent = commentList.stream()
                .collect(Collectors.groupingBy(gc -> Optional.ofNullable(gc.getParentId()).orElse(0L)));
        List<GatheringCommentRs> rootComment = groupedByParent.getOrDefault(0L, new ArrayList<>()).stream()
                .map(gatheringMapper::toGatheringCommentRs)
                .toList();
        rootComment.forEach(c -> c.setComments(groupedByParent.getOrDefault(c.getId(), new ArrayList<>()).stream()
                .map(gatheringMapper::toGatheringCommentRs)
                .toList()));
        return gatheringMapper.toGatheringDetailRs(gatheringDetailDto, genres, participants, rootComment);
    }

    public Page<GatheringRs> findByThemeId(Long userId, Pageable pageable, Long themeId) {
        Page<GatheringDto> gatheringDtoList = gatheringJooqRepository.findByThemeId(userId, pageable, themeId);
        List<Long> themeIdList = gatheringDtoList.getContent().stream().map(GatheringDto::getThemeId).toList();
        Map<Long, List<String>> genreListMap = genreJooqRepository.findAllByThemeIdList(themeIdList);
        List<GatheringRs> contents = gatheringDtoList.getContent().stream().map(g -> gatheringMapper.toGatheringRs(g, genreListMap.getOrDefault(g.getThemeId(), Collections.emptyList()))).toList();
        return new PageImpl<>(contents, pageable, gatheringDtoList.getTotalElements());
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
}