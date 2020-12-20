package club.gclmit.chaos.web.result;

/**
 * <p>
 * REST API 响应状态码
 * </p>
 *
 * @author gclm
 */
public enum ApiCode {

    //--------------------------------------------
    // 基础类
    //--------------------------------------------
    /**
     * 操作成功
     **/
    OK(0, "操作成功"),

    /**
     * 未获得数据
     **/
    FAIL(1, "未获得数据"),

    //------------------------------------
    // 登录类
    //--------------------------------------------

    /**
     * 没有权限
     **/
    NOT_PERMISSION(10000, "没有权限"),

    /**
     * 验证码校验异常
     **/
    VERIFICATION_CODE_EXCEPTION(10001, "验证码校验异常"),

    /**
     * 登录授权异常
     **/
    AUTHENTICATION_EXCEPTION(10002, "登录授权异常"),

    /**
     * JWT Token解析异常
     **/
    JWTDECODE_EXCEPTION(10003, "Token解析异常"),

    /**
     * 登录授权异常
     **/
    ACCOUNT_PASSWORD_FAIL(10004, "账号或密码错误"),


    //--------------------------------------------
    // 基本业务类
    //--------------------------------------------

    /**
     * 请求参数校验异常
     **/
    PARAMETER_EXCEPTION(20001, "请求参数校验异常"),

    /**
     * 请求参数解析异常
     **/
    PARAMETER_PARSE_EXCEPTION(20002, "请求参数解析异常"),

    /**
     * HTTP内容类型异常
     **/
    HTTP_MEDIA_TYPE_EXCEPTION(20003, "HTTP内容类型异常"),

    /**
     * 系统异常
     **/
    SYSTEM_EXCEPTION(20004, "系统异常"),

    /**
     * 业务处理异常
     **/
    BUSINESS_EXCEPTION(20005, "业务处理异常"),

    /**
     * 数据库处理异常
     **/
    DAO_EXCEPTION(20006, "数据库处理异常");

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
     * @author 孤城落寞
     * @param code     状态码
     * @param message  请求消息
     */
    ApiCode(Integer code,  String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * <p>
     *  根据响应状态码找到枚举对象
     * </p>
     *
     * @author 孤城落寞
     * @param apiCode 状态码
     * @return ApiCode
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
