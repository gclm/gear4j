package club.gclmit.chaos.core.exception;

/**
 * <p>
 * Chaos Core 模块异常类封装
 * </p>
 *
 * @author: gclm
 * @since 1.8
 */
public class ChaosCoreException extends AbstractChaosException{

    private static final long serialVersionUID = 1L;

    public ChaosCoreException() {
        super();
    }

    public ChaosCoreException(String message) {
        super(message);
    }

    public ChaosCoreException(String messageTemplate, Object... params) {
        super(messageTemplate, params);
    }

    public ChaosCoreException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChaosCoreException(Throwable cause, String messageTemplate, Object... params) {
        super(cause, messageTemplate, params);
    }
}
