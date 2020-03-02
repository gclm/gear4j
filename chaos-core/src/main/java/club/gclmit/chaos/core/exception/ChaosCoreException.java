package club.gclmit.chaos.core.exception;

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
public class ChaosCoreException extends AbstractChaosException {

    public ChaosCoreException(String message) {
        super(message);
    }

    public ChaosCoreException(Integer code, String message) {
        super(code, message);
    }

    public ChaosCoreException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChaosCoreException(Integer code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
