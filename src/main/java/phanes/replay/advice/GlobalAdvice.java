package phanes.replay.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import phanes.replay.exception.IllegalAccessException;
import phanes.replay.exception.LikeNotFoundException;
import phanes.replay.exception.UnAuthenticateException;
import phanes.replay.utils.LogUtils;

@Slf4j
@RestControllerAdvice
public class GlobalAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleUnExpectedError(Exception ex) {
        log.error("Unexpected error", ex);
    }

    @ExceptionHandler(UnAuthenticateException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void handleIllegalAccess(UnAuthenticateException ex) {
        log.error("unauthenticate exception", ex);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleUnExpectedError(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage());
    }

    @ExceptionHandler(IllegalAccessException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void handleIllegalAccess(IllegalAccessException ex) {
        log.error("{} \t 쿼리 - userId: {}, hostId: {}", ex.getMessage(), ex.getUserId(), ex.getHostId());
    }

    @ExceptionHandler(LikeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleLikeNotFound(LikeNotFoundException ex) {
        log.error("{} \t 쿼리 - {}", ex.getMessage(), LogUtils.toLogString(ex.getQueryMaps()));
    }
}