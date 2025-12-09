package phanes.replay.utils;

import jakarta.annotation.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileUtils {

    public static Boolean isEmpty(MultipartFile file) {
        return file == null || file.isEmpty();
    }

    public static String getExtension(@Nullable String fileName) {
        if(fileName == null) {
            throw new RuntimeException();
        }
        String extension = StringUtils.getFilenameExtension(fileName);
        if (extension == null) {
            throw new RuntimeException();
        }
        return extension.toLowerCase();
    }
}