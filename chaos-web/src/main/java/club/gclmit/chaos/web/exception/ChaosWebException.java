package club.gclmit.chaos.web.exception;

import club.gclmit.chaos.http.exception.AbstractChaosException;

/**
 * <p>
 *  chaos 存储模块的异常处理
 * </p>
 *
 * @author gclm
 */
public class ChaosWebException extends AbstractChaosException {

    public ChaosWebException(String message) {
        super(message);
    }

    public ChaosWebException(String messageTemplate, Object... params) {
        super(messageTemplate, params);
    }

    public ChaosWebException(String message, Throwable cause) {
        super(message, cause);
    }

}
