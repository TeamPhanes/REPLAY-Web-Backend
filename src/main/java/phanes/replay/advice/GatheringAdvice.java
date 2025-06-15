package phanes.replay.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import phanes.replay.exception.CommentNotFoundException;
import phanes.replay.exception.GatheringNotFoundException;

@Slf4j
@RestControllerAdvice
public class GatheringAdvice {

    @ExceptionHandler(GatheringNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleGatheringNotFound(GatheringNotFoundException ex) {
        log.error(ex.getMessage());
    }

    @ExceptionHandler(CommentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleCommentNotFound(CommentNotFoundException ex) {
        log.error(ex.getMessage());
    }
}
