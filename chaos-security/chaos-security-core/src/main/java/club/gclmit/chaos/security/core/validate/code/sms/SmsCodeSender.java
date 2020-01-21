package club.gclmit.chaos.security.core.validate.code.sms;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author: gclm
 * @date: 2019/12/13 5:06 下午
 * @version: V1.0
 * @since 1.8
 */
public interface SmsCodeSender {

    void send(String mobile,String code);
}
