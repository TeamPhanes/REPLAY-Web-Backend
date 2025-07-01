package phanes.replay.exception;

import lombok.Getter;

@Getter
public class ThemeNotFoundException extends RuntimeException {

    private final Long themeId;

    public ThemeNotFoundException(String message, Long themeId) {
        super(message);
        this.themeId = themeId;
    }
}