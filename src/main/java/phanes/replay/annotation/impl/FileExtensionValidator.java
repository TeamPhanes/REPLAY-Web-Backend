package phanes.replay.annotation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import phanes.replay.annotation.ValidateFileExtension;
import phanes.replay.utils.FileUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FileExtensionValidator implements ConstraintValidator<ValidateFileExtension, List<MultipartFile>> {

    private Set<String> allowed;

    @Override
    public void initialize(ValidateFileExtension annotation) {
        allowed = new HashSet<>(Arrays.asList(annotation.allowedExtensions()));
    }

    @Override
    public boolean isValid(List<MultipartFile> files, ConstraintValidatorContext context) {
        if (CollectionUtils.isEmpty(files)) {
            return true;
        }

        for (MultipartFile file : files) {
            String name = file.getOriginalFilename();
            if (name == null || !name.contains(".")) {
                return false;
            }
            String extension = FileUtils.getExtension(name);
            if (!allowed.contains(extension)) {
                return false;
            }
        }
        return true;
    }
}