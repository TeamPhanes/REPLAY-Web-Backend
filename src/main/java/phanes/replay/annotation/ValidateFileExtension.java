package phanes.replay.annotation;

import jakarta.validation.Constraint;
import phanes.replay.annotation.impl.FileExtensionValidator;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = FileExtensionValidator.class)
public @interface ValidateFileExtension {

    String message() default "지원하지 않는 확장자입니다.";

    String[] allowedExtensions() default {
            "jpg", "jpeg", "png", "gif", "webp"
    };
}