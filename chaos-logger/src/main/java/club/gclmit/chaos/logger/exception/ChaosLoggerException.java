package club.gclmit.chaos.logger.exception;

import club.gclmit.chaos.core.exception.AbstractChaosException;

/**
 * <p>
 *  chaos 日志模块的异常处理
 * </p>
 *
 * @author: gclm
 * @date: 2020/1/15 3:46 下午
 * @version: V1.0
 * @since 1.8
 */
public class ChaosLoggerException extends AbstractChaosException {

    public ChaosLoggerException(String message) {
        super(message);
    }

    public ChaosLoggerException(Integer code, String message) {
        super(code, message);
    }

    public ChaosLoggerException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChaosLoggerException(Integer code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
