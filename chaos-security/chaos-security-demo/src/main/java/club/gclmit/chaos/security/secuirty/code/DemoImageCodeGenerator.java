package club.gclmit.chaos.security.secuirty.code;

import club.gclmit.chaos.helper.logger.Logger;
import club.gclmit.chaos.helper.logger.LoggerServer;
import club.gclmit.chaos.security.core.validate.code.ValidateCodeGenerator;
import club.gclmit.chaos.security.core.validate.code.image.ImageCode;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author: gclm
 * @date: 2020/1/8 4:09 下午
 * @version: V1.0
 * @since 1.8
 */
//@Component("imageValidateCodeGenerator")
public class DemoImageCodeGenerator implements ValidateCodeGenerator {

    @Override
    public ImageCode generate(ServletWebRequest request) {
        Logger.info(LoggerServer.SPRING_SECURITY,"更高级的图形验证码生成器");
        return null;
    }
}
