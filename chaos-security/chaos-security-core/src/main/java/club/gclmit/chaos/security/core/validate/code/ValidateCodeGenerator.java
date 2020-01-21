package club.gclmit.chaos.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * <p>
 * 验证码生成器接口
 * </p>
 *
 * @author: gclm
 * @date: 2019/12/13 10:45 上午
 * @version: V1.0
 * @since 1.8
 */
public interface ValidateCodeGenerator {

    ValidateCode generate(ServletWebRequest request);
}
