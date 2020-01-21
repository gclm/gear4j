package club.gclmit.chaos.security.secuirty.code;

import club.gclmit.chaos.helper.logger.Logger;
import club.gclmit.chaos.helper.logger.LoggerServer;
import club.gclmit.chaos.security.core.validate.code.sms.DefaultSmsCodeSender;
import org.springframework.stereotype.Component;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author: gclm
 * @date: 2020/1/8 4:15 下午
 * @version: V1.0
 * @since 1.8
 */
@Component("smsCodeSender")
public class DemoSmsCodeSender extends DefaultSmsCodeSender {

    @Override
    public void send(String mobile, String code) {
        Logger.info(LoggerServer.SPRING_SECURITY,"当前手机号：[{}]\t短信验证码：[{}]",mobile,code);
    }
}
