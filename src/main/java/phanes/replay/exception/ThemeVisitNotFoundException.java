package phanes.replay.exception;

import lombok.Getter;

@Getter
public class ThemeVisitNotFoundException extends RuntimeException {

    private final Long userId;
    private final Long themeId;

    public ThemeVisitNotFoundException(String message, Long userId, Long themeId) {
        super(message);
        this.userId = userId;
        this.themeId = themeId;
    }
}