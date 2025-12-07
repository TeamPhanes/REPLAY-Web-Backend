package phanes.replay.annotation;

import jakarta.validation.Constraint;
import phanes.replay.annotation.impl.ImageMimeTypeValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ImageMimeTypeValidator.class)
public @interface ValidateImageFile {

    String message() default "허용되지 않은 이미지 타입입니다.";

    String[] allowedTypes() default {
            "image/jpeg",
            "image/jpg",
            "image/png",
            "image/gif",
            "image/webp"
    };
}