package club.gclmit.chaos.security.core.validate.code.image;

import club.gclmit.chaos.security.core.validate.code.ValidateCode;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * <p>
 *  图片验证码
 * </p>
 *
 * @author: gclm
 * @date: 2019/12/12 3:47 下午
 * @version: V1.0
 * @since 1.8
 */
public class ImageCode extends ValidateCode {

    /**
     * 图片验证码对象
     */
    private BufferedImage image;

    public ImageCode() {
    }

    /**
     * @param: imageCode
     * @param: code
     * @param: expireIn  过期时间，单位秒
     * @date 2019/12/12 3:53 下午
     * @throws
     */
    public ImageCode(BufferedImage image, String code, int expireIn) {
        super(code, expireIn);
        this.image = image;

    }

    public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
        super(code, expireTime);
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
