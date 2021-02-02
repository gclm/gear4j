package club.gclmit.chaos.core.lang.qrcode;

import club.gclmit.chaos.core.exception.ChaosException;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;
import java.util.Hashtable;

/**
 * 解析二维码工具类
 *
 * @author gclm
 */
public class DecodeQrCode {

    private static class DecodeQrCodeHolder{
        private static final DecodeQrCode INSTANCE = new DecodeQrCode();
    }

    /**
     * 单例模式
     *
     * @author gclm
     * @return club.gclmit.chaos.core.lang.qrcode.DecodeQrCode
     */
    public static DecodeQrCode getInstance() {
        return DecodeQrCodeHolder.INSTANCE;
    }

    /**
     * <p>
     * 解析二维码
     * </p>
     *
     * @param image 二维码
     * @return club.gclmit.chaos.core.lang.Barcode.Decoder
     * @author gclm
     */
    public Decoder from(BufferedImage image) {
        return new Decoder(image);
    }

    /**
     * <p>
     * 解析二维码
     * </p>
     *
     * @param stream 二维码
     * @return club.gclmit.chaos.core.lang.Barcode.Decoder
     * @throws IOException if an I/O error occurs
     * @author gclm
     */
    public Decoder from(InputStream stream) throws IOException {
        return new Decoder(ImageIO.read(stream));
    }

    /**
     * <p>
     * 解析二维码
     * </p>
     *
     * @param file 二维码
     * @return club.gclmit.chaos.core.lang.Barcode.Decoder
     * @throws IOException if an I/O error occurs
     * @author gclm
     */
    public Decoder from(File file) throws IOException {
        return new Decoder(ImageIO.read(file));
    }

    /**
     * <p>
     * 解析二维码
     * </p>
     *
     * @param path 二维码
     * @return club.gclmit.chaos.core.lang.Barcode.Decoder
     * @throws IOException if an I/O error occurs
     * @author gclm
     */
    public Decoder from(Path path) throws IOException {
        return from(path.toFile());
    }


    /**
     * <p>
     * 解析二维码
     * </p>
     *
     * @param url 二维码
     * @return club.gclmit.chaos.core.lang.Barcode.Decoder
     * @throws IOException if an I/O error occurs
     * @author gclm
     */
    public Decoder from(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty(
                "User-Agent",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.90 Safari/537.36");
        return new Decoder(ImageIO.read(connection.getInputStream()));
    }

    /**
     * <p>
     * 解析二维码
     * </p>
     *
     * @param bytes 二维码
     * @return club.gclmit.chaos.core.lang.Barcode.Decoder
     * @throws IOException if an I/O error occurs
     * @author gclm
     */
    public Decoder from(byte[] bytes) throws IOException {
        return new Decoder(ImageIO.read(new ByteArrayInputStream(bytes)));
    }

    /**
     * 解析条形码
     */
    public static class Decoder {
        private static final MultiFormatReader READER;

        static {
            READER = new MultiFormatReader();
        }

        private final BufferedImage image;

        private Decoder(BufferedImage image) {
            this.image = image;
        }

        public String decode() {
            /**
             *  com.google.zxing.client.j2se.BufferedImageLuminanceSource: 缓冲图像亮度源
             *  作用：将 java.awt.image.BufferedImage 转为 zxing 的缓冲图像亮度源
             *  BinaryBitmap：二进制位图
             *  HybridBinarizer： 用于读取二维码图像数据
             */
            BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));

            /**
             * 如果内容包含中文，则解码的字符集格式应该和编码时一致
             */
            Hashtable<DecodeHintType, String> hints = new Hashtable<>();
            hints.put(DecodeHintType.CHARACTER_SET, QrCode.DEFAULT_CHARACTER_SET);

            /**
             * 如果图片不是二维码图片，则 decode 抛出异常 com.google.zxing.NotFoundException
             * MultiFormatWriter 的 encode 用于对内容进行编码成 2D 矩阵
             * MultiFormatReader 的 decode 用于读取二进制位图数据
             */
            try {
                Result result = READER.decode(binaryBitmap, hints);
                READER.reset();
                return result.getText();
            } catch (NotFoundException e) {
                throw new ChaosException("该图片不是二维码图片", e);
            }
        }
    }
}
