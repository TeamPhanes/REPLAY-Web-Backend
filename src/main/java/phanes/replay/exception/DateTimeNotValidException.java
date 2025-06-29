package phanes.replay.exception;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class DateTimeNotValidException extends RuntimeException {

    private final LocalDateTime registrationStart;
    private final LocalDateTime registrationEnd;

    public DateTimeNotValidException(String message, LocalDateTime registrationStart, LocalDateTime registrationEnd) {
        super(message);
        this.registrationStart = registrationStart;
        this.registrationEnd = registrationEnd;
    }
}
