package phanes.replay.annotation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import phanes.replay.annotation.ValidateFileSize;
import phanes.replay.utils.FileUtils;

import java.util.List;

public class FileSizeValidator implements ConstraintValidator<ValidateFileSize, List<MultipartFile>> {

    private long minSize;
    private long maxSize;

    @Override
    public void initialize(ValidateFileSize annotation) {
        this.minSize = annotation.min();
        this.maxSize = annotation.max();
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

            long size = file.getSize();

            if (size < minSize || size > maxSize) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(String.format("파일 크기 허용 범위(%d ~ %d)를 초과하였습니다.", minSize, maxSize))
                        .addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}