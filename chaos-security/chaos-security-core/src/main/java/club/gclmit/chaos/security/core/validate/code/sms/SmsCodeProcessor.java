package club.gclmit.chaos.security.core.validate.code.sms;

import club.gclmit.chaos.security.core.validate.code.impl.AbstractValidateCodeProcessor;
import club.gclmit.chaos.security.core.validate.code.ValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * <p>
 *  短信验证码处理器
 * </p>
 *
 * @author: gclm
 * @date: 2019/12/13 5:56 下午
 * @version: V1.0
 * @since 1.8
 */
@Component("smsValidateCodeProcessor")
public class SmsCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {

    @Autowired
    private SmsCodeSender smsCodeSender;

    @Override
    protected void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
        String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), "mobile");
        smsCodeSender.send(mobile,validateCode.getCode());
    }
}
