package phanes.replay.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import phanes.replay.user.domain.enums.SocialType;

import java.util.Locale;

@Aspect
@Component
public class OAuth2ControllerAOP {

    @Around("@annotation(phanes.replay.annotation.ValidateSocialType)")
    public Object validateSocialType(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        String socialType = (String) args[0];

        try {
            Enum.valueOf(SocialType.class, socialType.toUpperCase(Locale.KOREA));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid social type");
        }

        return joinPoint.proceed();
    }
}