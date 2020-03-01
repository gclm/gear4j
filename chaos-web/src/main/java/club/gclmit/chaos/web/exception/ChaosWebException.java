package club.gclmit.chaos.web.exception;

import club.gclmit.chaos.core.exception.AbstractChaosException;

/**
 * <p>
 *  chaos 存储模块的异常处理
 * </p>
 *
 * @author: gclm
 * @date: 2020/1/15 3:46 下午
 * @version: V1.0
 * @since 1.8
 */
public class ChaosWebException extends AbstractChaosException {

    public ChaosWebException(String message) {
        super(message);
    }

    public ChaosWebException(Integer code, String message) {
        super(code, message);
    }

    public ChaosWebException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChaosWebException(Integer code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
