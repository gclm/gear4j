package club.gclmit.chaos.core.lang.avatar;

import club.gclmit.chaos.core.codec.Base64Utils;
import club.gclmit.chaos.core.exception.ChaosException;
import club.gclmit.chaos.core.io.FileUtils;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * 头像生成工具类
 *
 * @author gclm
 */
public abstract class AvatarGenerator {

    /**
     * 浏览器地址栏预览的base64编码头
     */
    public static final String BASE64_IMAGE_PREFIX = "data:image/png;base64,";

    /**
     * 图片格式
     */
    public static final String PNG = "png";

    /**
     * 图片做圆角处理
     *
     * @param image        待处理 BufferedImage
     * @param cornerRadius 角半径
     * @return 处理后的BufferedImage
     */
    public static BufferedImage makeRoundedCorner(BufferedImage image, int cornerRadius) {
        int w = image.getWidth();
        int h = image.getHeight();
        BufferedImage output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = output.createGraphics();
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius, cornerRadius));
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
        return output;
    }

    /**
     * 保存图片到本地文件
     *
     * @param bufferedImage 生成的 bufferedImage
     * @param file          保存的文件
     * @author gclm
     */
    public static void saveImageToFile(BufferedImage bufferedImage, File file) {
        String suffix = FileUtils.getSuffix(file);
        try {
            ImageIO.write(bufferedImage, suffix, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存图片到 输出流
     *
     * @param bufferedImage 生成的 bufferedImage
     * @author gclm
     * @return OutputStream
     */
    public static OutputStream saveImageToOutputStream(BufferedImage bufferedImage) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            ImageIO.write(bufferedImage, PNG, byteArrayOutputStream);
            return byteArrayOutputStream;
        } catch (IOException e) {
            throw new ChaosException("头像生成失败", e);
        }
    }

    /**
     * 保存图片到 base64
     *
     * @param bufferedImage 生成的 bufferedImage
     * @author gclm
     * @return 字符串
     */
    public static String saveImageToBase64(BufferedImage bufferedImage) {
        ByteArrayOutputStream stream = (ByteArrayOutputStream) saveImageToOutputStream(bufferedImage);
        return BASE64_IMAGE_PREFIX + Base64Utils.encodeToString(stream.toByteArray());
    }

    /**
     * 获得随机颜色
     *
     * @return 返回随机颜色
     */
    public static Color getRandomColor() {
        String[] beautifulColors =
                new String[]{"232,221,203", "205,179,128", "3,101,100", "3,54,73", "3,22,52",
                        "237,222,139", "251,178,23", "96,143,159", "1,77,103", "254,67,101", "252,157,154",
                        "249,205,173", "200,200,169", "131,175,155", "229,187,129", "161,23,21", "34,8,7",
                        "118,77,57", "17,63,61", "60,79,57", "95,92,51", "179,214,110", "248,147,29",
                        "227,160,93", "178,190,126", "114,111,238", "56,13,49", "89,61,67", "250,218,141",
                        "3,38,58", "179,168,150", "222,125,44", "20,68,106", "130,57,53", "137,190,178",
                        "201,186,131", "222,211,140", "222,156,83", "23,44,60", "39,72,98", "153,80,84",
                        "217,104,49", "230,179,61", "174,221,129", "107,194,53", "6,128,67", "38,157,128",
                        "178,200,187", "69,137,148", "117,121,71", "114,83,52", "87,105,60", "82,75,46",
                        "171,92,37", "100,107,48", "98,65,24", "54,37,17", "137,157,192", "250,227,113",
                        "29,131,8", "220,87,18", "29,191,151", "35,235,185", "213,26,33", "160,191,124",
                        "101,147,74", "64,116,52", "255,150,128", "255,94,72", "38,188,213", "167,220,224",
                        "1,165,175", "179,214,110", "248,147,29", "230,155,3", "209,73,78", "62,188,202",
                        "224,160,158", "161,47,47", "0,90,171", "107,194,53", "174,221,129", "6,128,67",
                        "38,157,128", "201,138,131", "220,162,151", "137,157,192", "175,215,237", "92,167,186",
                        "255,66,93", "147,224,255", "247,68,97", "185,227,217"};
        int len = beautifulColors.length;
        Random random = new Random();
        String[] color = beautifulColors[random.nextInt(len)].split(",");
        return new Color(Integer.parseInt(color[0]), Integer.parseInt(color[1]),
                Integer.parseInt(color[2]));
    }

}
