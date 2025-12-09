package phanes.replay.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.sql.SQLSyntaxErrorException;

@Slf4j
@RestControllerAdvice
public class GlobalAdvice {

    @ExceptionHandler(SQLSyntaxErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleUnExpectedError(SQLSyntaxErrorException ex) {
        log.error("Unexpected error", ex);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handle(HandlerMethodValidationException ex) {
        ex.getParameterValidationResults().forEach(result -> {
            log.error("Validation target: {}", result.getMethodParameter());
            result.getResolvableErrors().forEach(error -> {
                log.error("  - error: {}", error.getDefaultMessage());
            });
        });
    }
}