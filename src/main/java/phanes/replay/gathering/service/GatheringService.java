package phanes.replay.gathering.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import phanes.replay.gathering.dto.GatheringDto;
import phanes.replay.gathering.dto.response.GatheringRs;
import phanes.replay.gathering.mapper.GatheringMapper;
import phanes.replay.gathering.repository.GatheringJooqRepository;
import phanes.replay.theme.repository.GenreJooqRepository;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GatheringService {

    private final GatheringJooqRepository gatheringJooqRepository;
    private final GenreJooqRepository genreJooqRepository;
    private final GatheringMapper  gatheringMapper;

    public Page<GatheringRs> findByThemeId(Long userId, Pageable pageable, Long themeId) {
        PageImpl<GatheringDto> gatheringDtoList = gatheringJooqRepository.findByThemeId(userId, pageable, themeId);
        List<Long> themeIdList = gatheringDtoList.getContent().stream().map(GatheringDto::getThemeId).toList();
        Map<Long, List<String>> genreListMap = genreJooqRepository.findAllByThemeIdList(themeIdList);
        List<GatheringRs> contents = gatheringDtoList.getContent().stream().map(g -> gatheringMapper.toGatheringRs(g, genreListMap.getOrDefault(g.getThemeId(), Collections.emptyList()))).toList();
        return new PageImpl<>(contents, pageable, gatheringDtoList.getTotalElements());
    }
}