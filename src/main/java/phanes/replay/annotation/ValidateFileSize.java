package phanes.replay.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import phanes.replay.annotation.impl.FileSizeValidator;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = FileSizeValidator.class)
public @interface ValidateFileSize {

    String message() default "업로드한 파일 중 크기가 허용 범위를 벗어난 파일이 있습니다.";

    long min() default 0;

    long max() default 10 * 1024 * 1024;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}