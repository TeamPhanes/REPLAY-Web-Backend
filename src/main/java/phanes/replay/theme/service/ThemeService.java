package phanes.replay.theme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.RestController;
import phanes.replay.review.repository.ReviewJooqRepository;
import phanes.replay.theme.domain.Theme;
import phanes.replay.theme.domain.ThemeLike;
import phanes.replay.theme.domain.ThemeVisit;
import phanes.replay.theme.dto.ThemeDetailDto;
import phanes.replay.theme.dto.ThemeDto;
import phanes.replay.theme.dto.response.ThemeDetailRs;
import phanes.replay.theme.dto.response.ThemePreviewRs;
import phanes.replay.theme.dto.response.ThemeRs;
import phanes.replay.theme.mapper.ThemeMapper;
import phanes.replay.theme.repository.*;
import phanes.replay.user.domain.User;
import phanes.replay.user.service.UserQueryService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ThemeService {

    private final UserQueryService  userQueryService;
    private final ThemeQueryService themeQueryService;
    private final ThemeLikeQueryService themeLikeQueryService;
    private final ThemeVisitQueryService themeVisitQueryService;
    private final ThemeJooqRepository themeJooqRepository;
    private final ThemeLikeJooqRepository themeLikeJooqRepository;
    private final ThemeVisitJooqRepository themeVisitJooqRepository;
    private final ThemeRepository themeRepository;
    private final ReviewJooqRepository reviewJooqRepository;
    private final GenreJooqRepository genreJooqRepository;
    private final ThemeMapper themeMapper;

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

    public Page<ThemeRs> findAll(Long userId, Pageable pageable, List<String> locations, List<String> genres) {
        Page<ThemeDto> themeDtoPage = themeJooqRepository.findAll(userId, pageable, locations, genres);
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

    public ThemeDetailRs findById(Long userId, Long themeId) {
        ThemeDetailDto detail = themeJooqRepository.findById(userId, themeId);
        List<String> genres = genreJooqRepository.findByThemeId(themeId);
        return themeMapper.toThemeDetailRs(detail, genres);
    }

    public Page<ThemeRs> findAllByLike(Long userId, Pageable pageable, List<String> locations, List<String> genres) {
        Page<ThemeDto> themeLikeDtoList = themeLikeJooqRepository.findAllByLike(userId, pageable, locations, genres);
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

    public void saveThemeLike(Long userId, Long themeId) {
        User user = userQueryService.findById(userId);
        Theme theme = themeQueryService.findById(themeId);
        ThemeLike themeLike = ThemeLike.builder()
                .user(user)
                .theme(theme)
                .build();
        themeLikeQueryService.save(themeLike);
    }

    public void saveThemeVisit(Long userId, Long themeId) {
        User user = userQueryService.findById(userId);
        Theme theme = themeQueryService.findById(themeId);
        ThemeVisit themeVisit = ThemeVisit.builder()
                .user(user)
                .theme(theme)
                .build();
        themeVisitQueryService.save(themeVisit);
    }

    public void deleteThemeLike(Long userId, Long themeId) {
        ThemeLike themeLike = themeLikeQueryService.findByUserIdAndThemeId(userId, themeId);
        themeLikeQueryService.delete(themeLike);
    }

    public void deleteThemeVisit(Long userId, Long themeId) {
        ThemeVisit themeVisit = themeVisitQueryService.findByUserIdAndThemeId(userId, themeId);
        themeVisitQueryService.delete(themeVisit);
    }
}