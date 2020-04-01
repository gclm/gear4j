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
public enum ApiCode {

    /**
     * 操作成功
     **/
    OK(200, "操作成功"),

    /**
     * 非法访问
     **/
    UNAUTHORIZED(401, "非法访问"),

    /**
     * 没有权限
     **/
    NOT_PERMISSION(403, "没有权限"),

    /**
     * 你请求的资源不存在
     **/
    NOT_FOUND(404, "你请求的资源不存在"),

    /**
     * 操作失败
     **/
    FAIL(500, "操作失败"),

    /**
     * 登录失败
     **/
    LOGIN_EXCEPTION(4000, "登录失败"),

    /**
     * 系统异常
     **/
    SYSTEM_EXCEPTION(5000, "系统异常"),

    /**
     * 请求参数校验异常
     **/
    PARAMETER_EXCEPTION(5001, "请求参数校验异常"),

    /**
     * 请求参数解析异常
     **/
    PARAMETER_PARSE_EXCEPTION(5002, "请求参数解析异常"),

    /**
     * HTTP内容类型异常
     **/
    HTTP_MEDIA_TYPE_EXCEPTION(5003, "HTTP内容类型异常"),

    /**
     * 系统处理异常
     **/
    SPRING_BOOT_PLUS_EXCEPTION(5100, "系统处理异常"),

    /**
     * 业务处理异常
     **/
    BUSINESS_EXCEPTION(5101, "业务处理异常"),

    /**
     * 数据库处理异常
     **/
    DAO_EXCEPTION(5102, "数据库处理异常"),

    /**
     * 验证码校验异常
     **/
    VERIFICATION_CODE_EXCEPTION(5103, "验证码校验异常"),

    /**
     * 登录授权异常
     **/
    AUTHENTICATION_EXCEPTION(5104, "登录授权异常"),

    /**
     * JWT Token解析异常
     **/
    JWTDECODE_EXCEPTION(5107, "Token解析异常");

    /**
     * 状态码
     */
    private  Integer code;

    /**
     * 响应消息
     */
    private  String message;

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
    private ApiCode(Integer code,  String message) {
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
    public static ApiCode getApiCode(Integer apiCode){
        ApiCode[] apiCodes = ApiCode.values();
        for (ApiCode code : apiCodes) {
            if (code.getCode().equals(apiCode)){
                return code;
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
