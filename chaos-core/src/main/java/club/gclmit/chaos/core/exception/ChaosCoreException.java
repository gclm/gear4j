package club.gclmit.chaos.core.exception;

/**
 * <p>
 * Chaos Core 模块异常类封装
 * </p>
 *
 * @author: gclm
 * @date: 2020/4/15 10:25 上午
 * @version: V1.0
 * @since 1.8
 */
public class ChaosCoreException extends AbstractChaosException{

    private static final long serialVersionUID = 1L;

    public ChaosCoreException() {
        super();
    }

    public ChaosCoreException(Throwable cause) {
        super(cause);
    }

    public ChaosCoreException(String message) {
        super(message);
    }

    public ChaosCoreException(String messageTemplate, Object... params) {
        super(messageTemplate, params);
    }

    public ChaosCoreException(Integer code, String message) {
        super(code, message);
    }

    public ChaosCoreException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChaosCoreException(Integer code, Throwable cause) {
        super(code, cause);
    }

    public ChaosCoreException(Integer code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
