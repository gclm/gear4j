package club.gclmit.chaos.exception;

/**
 * <p>
 *  chaos 权限模块的异常处理
 * </p>
 *
 * @author: gclm
 * @date: 2020/1/15 3:46 下午
 * @version: V1.0
 * @since 1.8
 */
public class ChaosSecurityException extends AbstractChaosException {

    public ChaosSecurityException(String message) {
        super(message);
    }

    public ChaosSecurityException(Integer code, String message) {
        super(code, message);
    }

    public ChaosSecurityException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChaosSecurityException(Integer code, String message, Throwable cause) {
        super(code, message, cause);
    }
}
