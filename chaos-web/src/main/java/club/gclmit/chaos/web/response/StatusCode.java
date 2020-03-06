package club.gclmit.chaos.web.response;


/**
 * <p>
 * REST API 响应状态码
 * </p>
 *
 * @author: gclm
 * @date: 2019/10/6 12:50 下午
 * @version: V1.0
 */
public enum StatusCode {

    OK(0, "操作成功"),
    FAIL(500, "操作失败"),


    UNAUTHORIZED(401, "非法访问"),
    NOT_PERMISSION(403, "没有权限"),
    NOT_FOUND(404, "你请求的资源不存在"),
    PARAMETER_PARSE_EXCEPTION(405,"请求参数解析异常"),
    HTTP_MEDIA_TYPE_EXCEPTION(415,"HTTP Media 类型异常"),

    LOGIN_EXCEPTION(5001,"登陆失败"),
    SYSTEM_EXCEPTION(5002,"系统异常!"),
    PARAMETER_EXCEPTION(5003,"请求参数校验异常");

    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 响应消息
     */
    private final String message;


    /**
     * <p>
     *  私有化构造器
     * </p>
     *
     * @summary 概要描述
     * @author 孤城落寞
     * @param: code
     * @param: message
     * @date 2019/10/6 1:01 下午
     * @throws
     */
    private StatusCode(final Integer code, final String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * <p>
     *  根据响应状态码找到枚举对象
     * </p>
     *
     * @summary 根据响应状态码找到枚举对象
     * @author 孤城落寞
     * @param: code
     * @date 2019/10/6 1:00 下午
     * @throws
     */
    public static StatusCode getAPIStatusCode(Integer code){
        StatusCode[] statusCodes = StatusCode.values();
        for (StatusCode statusCode : statusCodes) {
            if (statusCode.getCode().equals(code)){
                return statusCode;
            }
        }
        return OK;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
