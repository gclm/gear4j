package club.gclmit.chaos.core.exception;

import club.gclmit.chaos.core.util.StringUtils;

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

    private static final long serialVersionUID = 1L;

    public ChaosCoreException() {
    }

    public ChaosCoreException(String msg) {
        super(msg);
    }


    public ChaosCoreException(String messageTemplate, Object... params) {
        super(StringUtils.format(messageTemplate, params));
    }

    public ChaosCoreException(Throwable throwable) {
        super(throwable);
    }

    public ChaosCoreException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

    public ChaosCoreException(Integer status, String msg) {
        super(status, msg);
    }

    public ChaosCoreException(Integer status, Throwable throwable) {
        super(status, throwable);
    }

    public ChaosCoreException(Integer status, String msg, Throwable throwable) {
        super(status, msg, throwable);
    }}
