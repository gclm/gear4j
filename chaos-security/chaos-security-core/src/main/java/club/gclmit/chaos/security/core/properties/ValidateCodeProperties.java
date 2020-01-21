package club.gclmit.chaos.security.core.properties;

/**
 * <p>
 *  验证码
 * </p>
 *
 * @author: gclm
 * @date: 2019/12/12 5:39 下午
 * @version: V1.0
 * @since 1.8
 */
public class ValidateCodeProperties {

    private ImageCodeProperties image = new ImageCodeProperties();

    private SmsCodeProperties sms = new SmsCodeProperties();
    
    public SmsCodeProperties getSms() {
        return sms;
    }

    public void setSms(SmsCodeProperties sms) {
        this.sms = sms;
    }

    public ImageCodeProperties getImage() {
        return image;
    }

    public void setImage(ImageCodeProperties image) {
        this.image = image;
    }
}
