package phanes.replay.gathering.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import phanes.replay.gathering.domain.Gathering;
import phanes.replay.gathering.domain.GatheringLike;
import phanes.replay.gathering.dto.GatheringDto;
import phanes.replay.gathering.dto.response.GatheringRs;
import phanes.replay.gathering.mapper.GatheringMapper;
import phanes.replay.gathering.repository.GatheringJooqRepository;
import phanes.replay.theme.repository.GenreJooqRepository;
import phanes.replay.user.domain.User;
import phanes.replay.user.service.UserQueryService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GatheringService {

    private final UserQueryService userQueryService;
    private final GatheringQueryService gatheringQueryService;
    private final GatheringLikeQueryService gatheringLikeQueryService;
    private final GatheringJooqRepository gatheringJooqRepository;
    private final GenreJooqRepository genreJooqRepository;
    private final GatheringMapper  gatheringMapper;

    public Page<GatheringRs> findAll(Long userId, Pageable pageable, List<String> locations, List<String> genres) {
        Page<GatheringDto> gatheringDtoList = gatheringJooqRepository.findAll(userId, pageable, locations, genres);
        List<Long> themeIdList = gatheringDtoList.getContent().stream().map(GatheringDto::getThemeId).toList();
        Map<Long, List<String>> genreListMap = genreJooqRepository.findAllByThemeIdList(themeIdList);
        List<GatheringRs> contents = gatheringDtoList.getContent().stream().map(g -> gatheringMapper.toGatheringRs(g, genreListMap.getOrDefault(g.getThemeId(), Collections.emptyList()))).toList();
        return new PageImpl<>(contents, pageable, gatheringDtoList.getTotalElements());
    }

    public Page<GatheringRs> findByThemeId(Long userId, Pageable pageable, Long themeId) {
        PageImpl<GatheringDto> gatheringDtoList = gatheringJooqRepository.findByThemeId(userId, pageable, themeId);
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
}