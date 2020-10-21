package club.gclmit.chaos.starter;

import club.gclmit.chaos.core.exception.AbstractChaosException;
import club.gclmit.chaos.core.exception.ChaosCoreException;
import club.gclmit.chaos.core.web.result.Result;
import club.gclmit.chaos.logger.exception.ChaosLoggerException;
import club.gclmit.chaos.storage.exception.ChaosStorageException;
import club.gclmit.chaos.web.exception.ChaosWebException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 统一异常处理控制器
 * </p>
 *
 * @author 孤城落寞
 */
@RestController
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理 validate 异常
     *
     * @param exception 异常
     * @return club.gclmit.chaos.web.response.Result
     * @author gclm
     */
    @ExceptionHandler(value = {
            BindException.class,
            MethodArgumentNotValidException.class
    })
    public Result validationExceptionHandler(Exception exception) {

        BindingResult bindResult = null;
        if (exception instanceof BindException) {
            bindResult = ((BindException) exception).getBindingResult();
        } else if (exception instanceof MethodArgumentNotValidException) {
            bindResult = ((MethodArgumentNotValidException) exception).getBindingResult();
        }
        StringBuilder message = new StringBuilder();

        if (bindResult != null && bindResult.hasErrors()) {
            bindResult.getAllErrors().forEach(objectError -> {
                message.append(objectError.getDefaultMessage()).append(",");
            });
        } else {
            message.append("系统繁忙，请稍后重试...");
        }
        return Result.fail(message.substring(0, message.length() - 1));
    }

    /**
     * chaos组件相关的异常
     *
     * @param exception 异常
     * @return club.gclmit.chaos.web.response.Result
     * @author gclm
     */
    @ExceptionHandler(value = {
            AbstractChaosException.class,
            ChaosCoreException.class,
            ChaosStorageException.class,
            ChaosWebException.class,
            ChaosLoggerException.class
    })
    public Result chaosException(Exception exception) {

        return Result.fail(exception.getMessage());
    }
}
