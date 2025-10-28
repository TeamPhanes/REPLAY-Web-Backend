package phanes.replay.exception;

import java.io.IOException;

public class UploadFailException extends RuntimeException {

    public UploadFailException(String message, IOException e) {
        super(message, e);
    }
}