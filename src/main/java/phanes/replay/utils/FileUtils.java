package phanes.replay.utils;

import org.springframework.web.multipart.MultipartFile;

public class FileUtils {

    public static Boolean isEmpty(MultipartFile file) {
        return file == null || file.isEmpty();
    }

    public static String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
    }
}