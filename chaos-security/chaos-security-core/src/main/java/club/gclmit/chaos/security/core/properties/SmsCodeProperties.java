package club.gclmit.chaos.security.core.properties;

/**
 * <p>
 *  图片验证码配置项
 * </p>
 *
 * @author: gclm
 * @date: 2019/12/12 5:35 下午
 * @version: V1.0
 * @since 1.8
 */
public class SmsCodeProperties {

    /**
     *  验证码数字长度
     */
    private int length = 6;

    /**
     *  过期时间
     */
    private int expireIn = 60;

    /**
     * 需要拦截的 URL
     */
    private String url;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    public int getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(int expireIn) {
        this.expireIn = expireIn;
    }
}
