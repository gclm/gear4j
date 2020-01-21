package club.gclmit.chaos.security.core.validate.code.image;

import club.gclmit.chaos.security.core.validate.code.impl.AbstractValidateCodeProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;

/**
 * <p>
 * 图片验证码处理器
 * </p>
 *
 * @author: gclm
 * @date: 2019/12/13 5:54 下午
 * @version: V1.0
 * @since 1.8
 */
@Component("imageValidateCodeProcessor")
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {

    @Override
    protected void send(ServletWebRequest request, ImageCode imageCode) throws Exception {
        ImageIO.write(imageCode.getImage(),"JPEG",request.getResponse().getOutputStream());
    }
}
