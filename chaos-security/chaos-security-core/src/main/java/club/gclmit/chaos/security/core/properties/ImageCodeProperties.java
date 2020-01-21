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
public class ImageCodeProperties extends  SmsCodeProperties{


    /**
     * 宽
     */
    private int width = 67;

    /**
     *  高
     */
    private int height = 23;

    public ImageCodeProperties() {
        setLength(4);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
