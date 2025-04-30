package phanes.replay.theme.service;

import phanes.replay.theme.dto.ThemeListResponse;
import phanes.replay.theme.dto.ThemeSearchRequest;

import java.util.List;

public interface ThemeService {
    List<ThemeListResponse> getThemes(ThemeSearchRequest request, Long userId);
}
