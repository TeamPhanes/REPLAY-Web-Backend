package phanes.replay.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import phanes.replay.exception.UserNotFoundException;

@Slf4j
@RestControllerAdvice
public class UserAdvice {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleUserNotFound(UserNotFoundException ex) {
        log.error(ex.getMessage());
    }
}
