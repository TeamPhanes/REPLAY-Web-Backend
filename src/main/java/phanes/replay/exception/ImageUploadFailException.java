package phanes.replay.exception;

public class ImageUploadFailException extends RuntimeException {

    public ImageUploadFailException(String message, Throwable cause) {
        super(message, cause);
    }
}