package club.gclmit.chaos.core.exception;

import club.gclmit.chaos.core.util.StringUtils;

/**
 * <p>
 *  封装该项目的所以异常的父类
 *  该类继承运行时异常。
 * </p>
 *
 * @author gclm
 */
public class ChaosException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ChaosException() {
    }

    public ChaosException(Throwable cause) {
        super(cause);
    }

    public ChaosException(String message) {
        super(message);
    }

    public ChaosException(String messageTemplate, Object... params) {
        super(StringUtils.format(messageTemplate, params));
    }

    public ChaosException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChaosException(Throwable cause,String messageTemplate, Object... params) {
        super(StringUtils.format(messageTemplate, params),cause);
    }
}
