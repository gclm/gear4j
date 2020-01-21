package club.gclmit.chaos.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * <p>
 *   校验码处理器，封装不同校验码的处理逻辑
 * </p>
 *
 * @author: gclm
 * @date: 2019/12/13 5:15 下午
 * @version: V1.0
 * @since 1.8
 */
public interface ValidateCodeProcessor {

    String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";

    /**
     * 创建校验码
     */
    void create(ServletWebRequest request) throws Exception;

    /**
     *  效验验证码
     */
    void validate(ServletWebRequest request);

}
