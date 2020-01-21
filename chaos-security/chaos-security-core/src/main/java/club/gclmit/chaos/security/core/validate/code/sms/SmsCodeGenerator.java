package club.gclmit.chaos.security.core.validate.code.sms;

import club.gclmit.chaos.security.core.properties.SecurityProperties;
import club.gclmit.chaos.security.core.validate.code.ValidateCodeGenerator;
import club.gclmit.chaos.security.core.validate.code.ValidateCode;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * <p>
 * 图片验证码生成器
 * </p>
 *
 * @author: gclm
 * @date: 2019/12/13 10:46 上午
 * @version: V1.0
 * @since 1.8
 */
@Component("smsValidateCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public ValidateCode generate(ServletWebRequest request) {
        String code = RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength());
        return new ValidateCode(code,securityProperties.getCode().getSms().getExpireIn());
    }

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}
