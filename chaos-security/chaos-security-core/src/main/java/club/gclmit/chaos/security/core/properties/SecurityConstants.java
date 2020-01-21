package club.gclmit.chaos.security.core.properties;

/**
 * <p>
 *  权限相关的常量
 * </p>
 *
 * @author: gclm
 * @date: 2019/12/15 4:40 下午
 * @version: V1.0
 * @since 1.8
 */
public class SecurityConstants {

    /**
     * 默认的处理验证码的 url 前缀
     */
    public static final String DEFAULT_VALIDATE_CODE_URL_PREFIX = "/code";

    /**
     *  当请求需要身份认证的时，默认跳转的URL
     */
    public static final  String DEFAULT_UNAUTHENTICATION_URL = "/authentication/require";

    /**
     * 默认的用户名密码登录处理 URL
     */
    public static final  String DEFAULT_LOGIN_PROCESSING_URL_FORM = "/authentication/form";

    /**
     * 默认的手机验证码登录请求处理 url
     */
    public static final  String DEFAULT_LOGIN_PROCESSING_URL_MOBILE = "/authentication/mobile";

    /**
     * 默认登录页面
     */
    public static final  String DEFAULT_LOGIN_PAGE_URL = "/chaos-signIn.html";

    /**
     *  验证图片验证码时，http 请求中默认的携带图片验证码信息的参数的名称
     */
    public static final  String DEFAULT_PARAMETER_NAME_CODE_IMAGE = "imageCode";

    /**
     * 验证短信验证码时，http 请求中默认的携带短信验证码信息的参数的名称
     */
    public static final  String DEFAULT_PARAMETER_NAME_CODE_SMS = "smsCode";

    /**
     * 发送短信验证码 或 验证短信验证码时，传递手机号的参数的名称
     */
    public static final  String DEFAULT_PARAMETER_NAME_MOBILE = "mobile";

}
