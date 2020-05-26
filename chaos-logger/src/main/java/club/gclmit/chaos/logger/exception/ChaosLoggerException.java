package club.gclmit.chaos.logger.exception;

import club.gclmit.chaos.core.exception.AbstractChaosException;

/**
 * <p>
 *  chaos 日志模块的异常处理
 * </p>
 *
 * @author gclm
 */
public class ChaosLoggerException extends AbstractChaosException {

    public ChaosLoggerException() {
        super();
    }


    public ChaosLoggerException(String message) {
        super(message);
    }

    public ChaosLoggerException(String messageTemplate, Object... params) {
        super(messageTemplate, params);
    }

    public ChaosLoggerException(String message, Throwable cause) {
        super(message, cause);
    }

}
