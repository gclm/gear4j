package club.gclmit.chaos.core.util;

import club.gclmit.chaos.core.codec.Base64Utils;
import club.gclmit.chaos.core.exception.ChaosException;
import cn.hutool.core.util.RandomUtil;
import lombok.experimental.UtilityClass;
import sun.font.FontDesignMetrics;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;

/**
 * 头像生成
 *
 * @author gclm
 */
@UtilityClass
public class AvatarUtils {

    /**
     * 浏览器地址栏预览的base64编码头
     */
    public static final String BASE64_PREFIX = "data:image/png;base64,";

    /**
     * 图片格式
     */
    public static final String PNG = "png";

    //====================================================
    // 首字母风格

    /**
     * 生成 首字母风格
     *
     * @return Base64编码的头像
     */
    public static String getStartStyle(String content) {
        return BASE64_PREFIX + Base64Utils.encodeToString(createStartAvatar(content,PNG,200,200,90));
    }

    /**
     * 创建头像
     *
     * @param content    要画的文字
     * @param width      图片宽度
     * @param formatName 图片格式
     * @param height     图片高度
     * @param fontSize   字体大小（磅）
     */
    public static byte[] createStartAvatar(String content, String formatName, int width, int height, int fontSize) {
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()){

            BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            Graphics2D g = (Graphics2D) buffImg.getGraphics();
            //消除文字锯齿
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            //消除画图锯齿
            g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);
            g.setColor(Color.orange);
            g.fillRect(0, 0, width, height);
            // 创建字体，字体的大小应该根据图片的高度来定。
            Font font = new Font("楷体", Font.CENTER_BASELINE, fontSize);
            //字体的大小信息
            FontDesignMetrics metrics = FontDesignMetrics.getMetrics(font);
            //文字宽
            int fontWidth = metrics.stringWidth(content);
            //文字的高
            int fontHeight = metrics.getHeight();
            //基线(baseline)到该字体中大多数字符的升部(ascender)之间的距离
            int ascent = metrics.getAscent();
            g.setFont(font);
            g.setColor(Color.white);

            //drawString的坐标指的是文字左下角的坐标
            g.drawString(content, width / 2 - fontWidth / 2, height / 2 - fontHeight / 2 + ascent);
            ImageIO.write(buffImg, formatName, os);
            return os.toByteArray();
        } catch (Exception e) {
            throw new ChaosException("生成头像失败", e);
        }
    }


    //====================================================
    // github 风格

    /**
     * 生成 Github 风格的头像
     *
     * @return Base64编码的头像
     */
    public static String getGithubStyle() {
        long id = RandomUtil.randomLong(10000, 99999);
        return BASE64_PREFIX + Base64Utils.encodeToString(createGithubAvatar(id,PNG));
    }

    /**
     * 根据id生成一个头像，颜色随机。如果是使用hashCode()值的话，值可能为负数。需要要注意。
     *
     * @param id 传值id
     * @return 返回 byte数据的头像
     */
    private static byte[] createGithubAvatar(long id, String formatName) {
        int width = 20;
        int grid = 5;
        int padding = width / 2;
        int size = width * grid + width;
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        Graphics2D _2d = img.createGraphics();
        _2d.setColor(new Color(240, 240, 240));
        _2d.fillRect(0, 0, size, size);
        _2d.setColor(randomColor(80, 200));
        char[] chars = createIdent(id);
        int i = chars.length;
        for (int x = 0; x < Math.ceil(grid / 2.0); x++) {
            for (int y = 0; y < grid; y++) {
                if (chars[--i] < 53) {
                    _2d.fillRect((padding + x * width), (padding + y * width), width, width);
                    if (x < Math.floor(grid / 2)) {
                        _2d.fillRect((padding + ((grid - 1) - x) * width), (padding + y * width), width, width);
                    }
                }
            }
        }
        _2d.dispose();
        try(ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            ImageIO.write(img, formatName, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new ChaosException("头像生成失败", e);
        }
    }

    private static Color randomColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(Math.abs(bc - fc));
        int g = fc + random.nextInt(Math.abs(bc - fc));
        int b = fc + random.nextInt(Math.abs(bc - fc));
        return new Color(r, g, b);
    }

    private static char[] createIdent(long id) {
        BigInteger biContent = new BigInteger((id + "").getBytes());
        BigInteger bi = new BigInteger(id + "identical" + id, 36);
        bi = bi.xor(biContent);
        return bi.toString(10).toCharArray();
    }
}
