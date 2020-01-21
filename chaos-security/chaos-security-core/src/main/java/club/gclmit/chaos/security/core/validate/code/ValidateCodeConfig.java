package club.gclmit.chaos.security.core.validate.code;

import club.gclmit.chaos.security.core.properties.SecurityProperties;
import club.gclmit.chaos.security.core.validate.code.image.ImageCodeGenerator;
import club.gclmit.chaos.security.core.validate.code.sms.DefaultSmsCodeSender;
import club.gclmit.chaos.security.core.validate.code.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * 验证码效验器配置
 * </p>
 *
 * @author: gclm
 * @date: 2019/12/13 10:59 上午
 * @version: V1.0
 * @since 1.8
 */
@Configuration
public class ValidateCodeConfig {

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * <p>
     *  spring 容器中如果存在 imageCodeGenerate 则就不会初始化Bean
     * </p>
     *
     * @author gclm
     * @date 2019/12/13 11:02 上午 club.gclmit.security.validate.code.ValidateCodeGenerate
     * @throws
     */
    @Bean
    @ConditionalOnMissingBean(name = "imageValidateCodeGenerator")
    public ValidateCodeGenerator imageValidateCodeGenerator(){
        ImageCodeGenerator imageCodeGenerator = new ImageCodeGenerator();
        imageCodeGenerator.setImageProperties(securityProperties.getCode().getImage());
        return imageCodeGenerator;
    }

    @Bean
    @ConditionalOnMissingBean(SmsCodeSender.class)
    public SmsCodeSender smsCodeSender(){
        return new DefaultSmsCodeSender();
    }
}
