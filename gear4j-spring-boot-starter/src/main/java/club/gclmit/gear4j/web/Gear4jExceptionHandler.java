package club.gclmit.gear4j.web;

import org.springframework.web.bind.annotation.ExceptionHandler;

import club.gclmit.gear4j.core.exception.Gear4jException;
import club.gclmit.gear4j.domain.result.ApiResult;

/**
 * Gear4j 异常处理器
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/7/17 02:31
 * @since jdk11
 */
public class Gear4jExceptionHandler extends Gear4jGlobalExceptionHandler {

    /**
     * chaos组件相关的异常
     *
     * @param exception 异常
     * @return {@link ApiResult}
     */
    @ExceptionHandler(value = {Gear4jException.class})
    public ApiResult chaosException(Exception exception) {
        return ApiResult.fail(exception.getMessage());
    }
}
