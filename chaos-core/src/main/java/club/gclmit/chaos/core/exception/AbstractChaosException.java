package club.gclmit.chaos.core.exception;


import club.gclmit.chaos.core.util.StringUtils;

/**
 * <p>
 *  封装该项目的所以异常的父类
 *  该类继承运行时异常。
 * </p>
 *
 * @author: gclm
 * @date: 2020/1/15 3:49 下午
 * @version: V1.0
 * @since 1.8
 */
public abstract class AbstractChaosException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AbstractChaosException() {
    }

    public AbstractChaosException(Throwable cause) {
        super(cause);
    }

    public AbstractChaosException(String message) {
        super(message);
    }

    public AbstractChaosException(String messageTemplate, Object... params) {
        super(StringUtils.format(messageTemplate, params));
    }

    public AbstractChaosException(String message, Throwable cause) {
        super(message, cause);
    }

}
