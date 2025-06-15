package phanes.replay.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import phanes.replay.exception.ImageUploadFailException;
import phanes.replay.exception.ReviewNotFountException;
import phanes.replay.exception.ThemeNotFoundException;

@Slf4j
@RestControllerAdvice
public class ThemeAdvice {

    @ExceptionHandler(ThemeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleThemeNotFound(ThemeNotFoundException ex) {
        log.error(ex.getMessage());
    }

    @ExceptionHandler(ReviewNotFountException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleReviewNotFound(ReviewNotFountException ex) {
        log.error(ex.getMessage());
    }

    @ExceptionHandler(ImageUploadFailException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleImageUploadFail(ImageUploadFailException ex) {
        log.error("image upload fail", ex);
    }
}
