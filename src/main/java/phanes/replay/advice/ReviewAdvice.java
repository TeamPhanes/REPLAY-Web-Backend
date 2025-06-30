package phanes.replay.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import phanes.replay.exception.ReviewNotFountException;
import phanes.replay.utils.LogUtils;

@Slf4j
@RestControllerAdvice
public class ReviewAdvice {

    @ExceptionHandler(ReviewNotFountException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleUnExpectedError(ReviewNotFountException ex) {
        log.error("{} \t 쿼리 - {}", ex.getMessage(), LogUtils.toLogString(ex.getQueryMaps()));
    }
}
