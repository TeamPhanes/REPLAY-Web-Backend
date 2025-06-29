package phanes.replay.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import phanes.replay.exception.*;
import phanes.replay.utils.LogUtils;

@Slf4j
@RestControllerAdvice
public class GatheringAdvice {

    @ExceptionHandler(GatheringNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleGatheringNotFound(GatheringNotFoundException ex) {
        log.error("{} \t 쿼리 - gatheringId: {}", ex.getMessage(), ex.getGatheringId());
    }

    @ExceptionHandler(GatheringContentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleGatheringContentNotFound(GatheringContentNotFoundException ex) {
        log.error("{} \t 쿼리 - gatheringId: {}", ex.getMessage(), ex.getGatheringId());
    }

    @ExceptionHandler(HostNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleHostNotFound(HostNotFoundException ex) {
        log.error("{} \t 쿼리 - {}", ex.getMessage(), LogUtils.toLogString(ex.getQueryMaps()));
    }

    @ExceptionHandler(DateTimeNotValidException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleDateTimeNotValidFound(DateTimeNotValidException ex) {
        log.error("{} \t 파라미터 - registrationStart: {}, registrationEnd: {}", ex.getMessage(), ex.getRegistrationStart(), ex.getRegistrationEnd());
    }

    @ExceptionHandler(CommentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleCommentNotFound(CommentNotFoundException ex) {
        log.error("{} \t 쿼리 - userId: {}, gatheringId: {}, commentId: {}", ex.getMessage(), ex.getUserId(), ex.getGatheringId(), ex.getCommentId());
    }
}
