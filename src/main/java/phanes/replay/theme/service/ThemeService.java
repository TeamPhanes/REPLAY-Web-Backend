package phanes.replay.theme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.RestController;
import phanes.replay.review.repository.ReviewJooqRepository;
import phanes.replay.theme.dto.ThemeDto;
import phanes.replay.theme.dto.response.ThemePreviewRs;
import phanes.replay.theme.dto.response.ThemeRs;
import phanes.replay.theme.mapper.ThemeMapper;
import phanes.replay.theme.repository.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ThemeService {

    private final ThemeJooqRepository themeJooqRepository;
    private final ThemeLikeJooqRepository themeLikeJooqRepository;
    private final ThemeVisitJooqRepository themeVisitJooqRepository;
    private final ThemeRepository themeRepository;
    private final ThemeMapper themeMapper;
    private final ReviewJooqRepository reviewJooqRepository;
    private final GenreJooqRepository genreJooqRepository;

    public List<ThemePreviewRs> findAllByThemePreviewOrderByLike(int size) {
        return themeJooqRepository.findAllOrderByThemeLike(size)
                .stream()
                .map(themeMapper::toThemePreview)
                .toList();
    }

    public List<ThemePreviewRs> findAllByThemePreviewOrderByCreatedAt(int size) {
        Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Order.desc("createdAt")));
        return themeRepository.findAllBy(pageable)
                .stream()
                .map(themeMapper::toThemePreview)
                .toList();
    }

    public Page<ThemeRs> findAll(Long userId, Pageable pageable, List<String> state, List<String> city, List<String> genres) {
        Page<ThemeDto> themeDtoPage = themeJooqRepository.findAll(userId, pageable, state, city, genres);
        List<Long> themeIdList = themeDtoPage.stream().map(ThemeDto::getId).toList();
        Map<Long, Long> reviewCountMap = reviewJooqRepository.countAllByThemeIdList(themeIdList);
        Map<Long, Double> scoreMap = reviewJooqRepository.aggregateAllByThemeIdList(themeIdList);
        Map<Long, List<String>> genreListMap = genreJooqRepository.findAllByThemeIdList(themeIdList);
        List<ThemeRs> content = themeDtoPage.stream()
                .map(t ->
                        themeMapper.toThemeRs(
                                t,
                                reviewCountMap.getOrDefault(t.getId(), 0L),
                                scoreMap.getOrDefault(t.getId(), 0.0),
                                genreListMap.getOrDefault(t.getId(), Collections.emptyList())))
                .toList();
        return new PageImpl<>(content, pageable, themeDtoPage.getTotalElements());
    }

    public Page<ThemeRs> findAllByLike(Long userId, Pageable pageable, List<String> state, List<String> city, List<String> genres) {
        Page<ThemeDto> themeLikeDtoList = themeLikeJooqRepository.findAllByLike(userId, pageable, state, city, genres);
        List<Long> themeIdList = themeLikeDtoList.stream().map(ThemeDto::getId).toList();
        Map<Long, Long> reviewCountMap = reviewJooqRepository.countAllByThemeIdList(themeIdList);
        Map<Long, Double> scoreMap = reviewJooqRepository.aggregateAllByThemeIdList(themeIdList);
        Map<Long, List<String>> genreListMap = genreJooqRepository.findAllByThemeIdList(themeIdList);
        List<ThemeRs> content = themeLikeDtoList.stream()
                .map(t ->
                        themeMapper.toThemeRs(
                                t,
                                reviewCountMap.getOrDefault(t.getId(), 0L),
                                scoreMap.getOrDefault(t.getId(), 0.0),
                                genreListMap.getOrDefault(t.getId(), Collections.emptyList())))
                .toList();
        return new PageImpl<>(content, pageable, themeLikeDtoList.getTotalElements());
    }

    public Page<ThemeRs> findAllByVisit(Long userId, Pageable pageable, List<String> state, List<String> city, List<String> genres) {
        Page<ThemeDto> themeVisitDtoList = themeVisitJooqRepository.findAllByVisit(userId, pageable, state, city, genres);
        List<Long> themeIdList = themeVisitDtoList.stream().map(ThemeDto::getId).toList();
        Map<Long, Long> reviewCountMap = reviewJooqRepository.countAllByThemeIdList(themeIdList);
        Map<Long, Double> scoreMap = reviewJooqRepository.aggregateAllByThemeIdList(themeIdList);
        Map<Long, List<String>> genreListMap = genreJooqRepository.findAllByThemeIdList(themeIdList);
        List<ThemeRs> content = themeVisitDtoList.stream()
                .map(t ->
                        themeMapper.toThemeRs(
                                t,
                                reviewCountMap.getOrDefault(t.getId(), 0L),
                                scoreMap.getOrDefault(t.getId(), 0.0),
                                genreListMap.getOrDefault(t.getId(), Collections.emptyList())))
                .toList();
        return new PageImpl<>(content, pageable, themeVisitDtoList.getTotalElements());
    }
}