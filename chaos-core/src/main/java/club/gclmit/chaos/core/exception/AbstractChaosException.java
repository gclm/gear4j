package club.gclmit.chaos.core.exception;


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

    /**
     * 异常状态码,默认为500
     */
    private Integer code = 500;

    /**
     * 错误消息
     */
    private String message = "Chaos组件发成异常";

    public AbstractChaosException(String message) {
        this.message = message;
    }

    public AbstractChaosException(Integer code , String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public AbstractChaosException(String message , Throwable cause) {
        super(cause);
        this.message = message;
    }

    public AbstractChaosException(Integer code , String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
