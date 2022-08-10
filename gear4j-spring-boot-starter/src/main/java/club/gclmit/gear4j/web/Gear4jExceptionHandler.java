package club.gclmit.gear4j.web;

import club.gclmit.gear4j.core.exception.Gear4jException;
import club.gclmit.gear4j.domain.result.ApiResult;
import club.gclmit.gear4j.safe.Gear4jSafeException;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
	@ExceptionHandler(value = {Gear4jException.class, Gear4jSafeException.class, Gear4jValidateException.class})
	public ApiResult<Object> gear4jException(Exception exception) {
		return ApiResult.fail(exception.getMessage());
	}
}
