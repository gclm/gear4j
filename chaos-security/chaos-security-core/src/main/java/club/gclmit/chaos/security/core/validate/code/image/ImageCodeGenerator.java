package club.gclmit.chaos.security.core.validate.code.image;

import club.gclmit.chaos.security.core.properties.ImageCodeProperties;
import club.gclmit.chaos.security.core.validate.code.ValidateCodeGenerator;
import club.gclmit.chaos.security.core.validate.code.ValidateCode;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

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
public class ImageCodeGenerator implements ValidateCodeGenerator {

    private ImageCodeProperties imageProperties;

    /**
     * <p>
     *  图片验证码生成器代码
     * </p>
     *
     * @author gclm
     * @param: request
     * @date 2019/12/13 10:58 上午 club.gclmit.security.validate.code.image.ImageCode
     * @throws
     */
    @Override
    public ValidateCode generate(ServletWebRequest request) {
        int width = ServletRequestUtils.getIntParameter(request.getRequest(),"width",imageProperties.getWidth());
        int height = ServletRequestUtils.getIntParameter(request.getRequest(),"height",imageProperties.getHeight());
        return  createImageCode(width,height,imageProperties.getLength(),imageProperties.getExpireIn());
    }

    /**
     * <p>
     *  图片验证码生成器工具代码
     * </p>
     *
     * @author gclm
     * @param: width  宽
     * @param: height 高
     * @param: length 长
     * @param: expireIn 过期时间
     * @date 2019/12/13 10:57 上午 club.gclmit.security.validate.code.image.ImageCode
     * @throws
     */
    private ImageCode createImageCode(int width,int height, int length , int expireIn) {

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics g = image.getGraphics();
        Random random = new Random();

        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        String sRand = "";
        for (int i = 0; i < length; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(rand, 13 * i + 6, 16);
        }

        g.dispose();

        return new ImageCode(image, sRand, expireIn);
    }


    /**
     * 生成随机背景条纹
     *
     * @param fc
     * @param bc
     * @return
     */
    private Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }


    public ImageCodeProperties getImageProperties() {
        return imageProperties;
    }

    public void setImageProperties(ImageCodeProperties imageProperties) {
        this.imageProperties = imageProperties;
    }


}
