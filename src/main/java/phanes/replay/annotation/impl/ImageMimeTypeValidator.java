package phanes.replay.annotation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.tika.Tika;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import phanes.replay.annotation.ValidateImageFile;
import phanes.replay.utils.FileUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ImageMimeTypeValidator implements ConstraintValidator<ValidateImageFile, List<MultipartFile>> {

    private static final Tika tika = new Tika();
    private Set<String> allowedMimeTypes;

    @Override
    public void initialize(ValidateImageFile constraintAnnotation) {
        this.allowedMimeTypes = new HashSet<>(Arrays.asList(constraintAnnotation.allowedTypes()));
    }

    @Override
    public boolean isValid(List<MultipartFile> files, ConstraintValidatorContext context) {
        if (CollectionUtils.isEmpty(files)) {
            return true;
        }

        for (MultipartFile file : files) {
            if (FileUtils.isEmpty(file)) {
                continue;
            }

            try (InputStream is = file.getInputStream()) {
                String detectedMimeType = tika.detect(is);
                if (detectedMimeType == null || !allowedMimeTypes.contains(detectedMimeType)) {
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate("허용되지 않는 이미지 타입입니다: " + detectedMimeType)
                            .addConstraintViolation();
                    return false;
                }
            } catch (IOException e) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("이미지 MIME 타입 검증 중 ")
                        .addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}