package club.gclmit.chaos.security.web.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.Clock;

/**
 * <p>
 *  时间切面
 * </p>
 *
 * @author: gclm
 * @date: 2019/12/4 9:26 上午
 * @version: V1.0
 * @since 1.8
 */
//@Aspect
//@Component
public class TimeAspect {

    @Around("execution(* club.gclmit.security.web.controller.UserController.*(..))")
    public Object handleTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("time aspect is start ..");
        for (Object  object: proceedingJoinPoint.getArgs()) {
            System.out.println(object);
        }
        long startTime = Clock.systemDefaultZone().millis();

        Object obj = proceedingJoinPoint.proceed();

        System.out.println("time aspect 耗时:" + (Clock.systemDefaultZone().millis() - startTime));
        System.out.println("time finish ..");

        return obj;
    }
}
