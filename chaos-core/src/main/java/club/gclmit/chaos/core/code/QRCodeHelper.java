package club.gclmit.chaos.core.code;

import club.gclmit.chaos.core.exception.ChaosCoreException;
import club.gclmit.chaos.core.file.FileHelper;
import club.gclmit.chaos.core.helper.IDHelper;
import club.gclmit.chaos.core.logger.Logger;
import club.gclmit.chaos.core.logger.LoggerServer;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.util.Assert;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * <p>
 * 二维码工具类
 * </p>
 *
 * @author: gclm
 * @date: 2019-10-22 10:52:00
 * @version: V1.0
 */
public class QRCodeHelper {

    /**
     * <p>
     * parseQRCodeByFile:根据文件解析二维码
     * </p>
     *
     * @throws
     * @author 孤城落寞
     * @param: filePath
     * @date 2019/10/22 21:43
     * @return: java.lang.String
     */
    public static String parseQRCode(String filePath) throws IOException, NotFoundException {
        return parseQRCode(new File(filePath));
    }

    /**
     * @param url
     * @description: 根据 url 解析二维码
     * @author 孤城落寞
     * @date: 2019-08-13
     * @return: java.lang.String
     */
    public static String parseQRCode(URL url) throws IOException, NotFoundException {
        BufferedImage bufferedImage = createBufferedImageParseURL(url);
        return parseQRCode(bufferedImage);
    }

    /**
     * @param file
     * @description: 根据文件解析二维码
     * @author 孤城落寞
     * @date: 2019-08-13
     * @return: java.lang.String
     */
    public static String parseQRCode(File file) throws IOException, NotFoundException {
        BufferedImage bufferedImage = createBufferedImageParseFile(file);
        return parseQRCode(bufferedImage);
    }

    /**
     *  根据文件类型创建 BufferedImage 对象
     *  原理：
     *   根据 二维码类型来采用不同的 ImageIO 操作，
     *   ImageIO.read(URL input) 读取网络图片文件转换为内存缓冲图像
     *          .read(File input) 读取本地图片文件...
     *          .read(InputStream input) 读取读取输入流
     *          .read(ImageInputStream stream) 读取图片输入流
     */

    /**
     * @param imgFile
     * @author 孤城落寞
     * @date: 2019-08-13
     * @return: java.awt.image.BufferedImage
     */
    private static BufferedImage createBufferedImageParseFile(File imgFile) throws IOException {
        Assert.isTrue(imgFile.exists(), "文件不存在");
        return ImageIO.read(imgFile);
    }

    /**
     * @param url
     * @author 孤城落寞
     * @date: 2019-08-13
     * @return: java.awt.image.BufferedImage
     */
    private static BufferedImage createBufferedImageParseURL(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty(
                "User-Agent",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.90 Safari/537.36");
        return ImageIO.read(connection.getInputStream());
    }

    /**
     * @param bufferedImage 图片文件
     * @description: 根据 本地/网络 二维码图片————————解析二维码内容
     * （注：图片必须是二维码图片，但也可以是微信用户二维码名片，上面有名称、头像也是可以的）
     * @author 孤城落寞
     * @date: 2019-08-13
     * @return: java.lang.String
     */
    private static String parseQRCode(BufferedImage bufferedImage) {

        /**
         *  com.google.zxing.client.j2se.BufferedImageLuminanceSource: 缓冲图像亮度源
         *  作用：将 java.awt.image.BufferedImage 转为 zxing 的缓冲图像亮度源
         *  BinaryBitmap：二进制位图
         *  HybridBinarizer： 用于读取二维码图像数据
         */
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));

        /**
         * 如果内容包含中文，则解码的字符集格式应该和编码时一致
         */
        Hashtable hints = new Hashtable();
        hints.put(DecodeHintType.CHARACTER_SET, QRCodeConfig.IMAGE_CHARACTER_ENCODE);

        /**
         * 如果图片不是二维码图片，则 decode 抛出异常 com.google.zxing.NotFoundException
         * MultiFormatWriter 的 encode 用于对内容进行编码成 2D 矩阵
         * MultiFormatReader 的 decode 用于读取二进制位图数据
         */
        Result result = null;
        try {
            result = new MultiFormatReader().decode(binaryBitmap, hints);
        } catch (NotFoundException e) {
            throw new ChaosCoreException("该图片不是二维码图片", e);
        }
        return result.getText();
    }

    /**
     * 二维码生成工具类
     * 孤城落寞 2019-02-15 10:03
     *
     * @param qrCode 工具类
     * @return java.lang.String
     * @throws IOException,WriterException
     */
    public static String generateQRCode(QRCode qrCode) {
        /**
         * com.google.zxing.EncodeHintType 编码提示类型，枚举类型
         *  EncodeHintType.CHARACTER_SET 设置字符编码类型
         *  EncodeHintType.ERROR_CORRECTION 误差校正级别，详情看 QrCode 的 errorLevel 属性
         *  EncodeHintType.MARGIN 设置二维码边框，详情看 QrCode 的 margin 属性
         */
        Map<EncodeHintType, Object> hists = new HashMap<EncodeHintType, Object>();
        hists.put(EncodeHintType.CHARACTER_SET, qrCode.getEncode());
        hists.put(EncodeHintType.ERROR_CORRECTION, qrCode.getErrorLevel());
        hists.put(EncodeHintType.MARGIN, qrCode.getMargin());

        try {

            /**
             * MultiFormatWriter:多格式写入，这是一个工厂类，里面重载了两个 encode 方法，用于写入条形码或二维码
             *      encode(String contents,BarcodeFormat format,int width, int height,Map<EncodeHintType,?> hints)
             *      contents:条形码/二维码内容
             *      format：编码类型，如 条形码，二维码 等
             *      width：码的宽度
             *      height：码的高度
             *      hints：码内容的编码类型
             * BarcodeFormat：枚举该程序包已知的条形码格式，即创建何种码，如 1 维的条形码，2 维的二维码 等
             * BitMatrix：位(比特)矩阵或叫2D矩阵，也就是需要的二维码
             */
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            BitMatrix bitMatrix = multiFormatWriter.encode(qrCode.getContent(), qrCode.getFormat(), qrCode.getWidth(), qrCode.getHeight(), hists);

            BufferedImage bufferedImage = bitMatrixToBufferedImage(bitMatrix, qrCode);


            if (QRCodeConfig.IMAGE_TYPE_FILE.equals(qrCode.getType())) {
                String path = qrCode.getGeneartePath();
                String id = IDHelper.getStringId();

                Logger.info(LoggerServer.CHAOS_CORE, "配置的文件路径:{}", path);

                StringBuilder filePath = new StringBuilder();

                if (path != null && path.lastIndexOf("/") != path.length() - 1) {
                    filePath.append(path).append("/");
                } else {
                    filePath.append(System.getProperty("user.dir")).append("/");
                }

                filePath.append(id).append(".").append(qrCode.getSuffix());
                File codeImgFile = FileHelper.autoJudgeFile(filePath.toString());

                Logger.info(LoggerServer.CHAOS_CORE, "生成的文件路径:{}", codeImgFile.getPath());

                ImageIO.write(bufferedImage, QRCodeConfig.IMAGE_FORMAT_NAME, codeImgFile);

                return codeImgFile.getPath();
            } else {
                /**
                 * 1. 创建字节输出流，将生成的二维码图片写入到创建的字节输出流里
                 */
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, QRCodeConfig.IMAGE_FORMAT_NAME, outputStream);

                /**
                 * 2.读取文件 --> 字节数组 --> 二进制
                 *
                 */
                byte[] bytes = outputStream.toByteArray();

                String code = Base64.getEncoder().encodeToString(bytes).trim();

                return code;
            }
        } catch (IOException e) {
            throw new ChaosCoreException("二维码图片合成失败(写入 ImageIO 失败)",e);
        } catch (WriterException e) {
            throw new ChaosCoreException("二维码图片合成失败，请稍后再试",e);
        }
    }

    /**
     * BitMatrix 转换为 BufferedImage
     * 孤城落寞 2019-02-15 14:17
     *
     * @param bitMatrix
     * @param qrCode
     * @return java.awt.image.BufferedImage
     */
    private static BufferedImage bitMatrixToBufferedImage(BitMatrix bitMatrix, QRCode qrCode) {

        int height = bitMatrix.getHeight();
        int width = bitMatrix.getWidth();

        /**java.awt.image.BufferedImage：具有图像数据的可访问缓冲图像，实现了 RenderedImage 接口
         * BitMatrix 的 get(int x, int y) 获取比特矩阵内容，指定位置有值，则返回true，将其设置为前景色，否则设置为背景色
         * BufferedImage 的 setRGB(int x, int y, int rgb) 方法设置图像像素
         *      x：像素位置的横坐标，即列
         *      y：像素位置的纵坐标，即行
         *      rgb：像素的值，采用 16 进制,如 0xFFFFFF 白色
         */
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bufferedImage.setRGB(x, y, bitMatrix.get(x, y) ? qrCode.getFroutColor() : qrCode.getBackgroudColor());
            }
        }
        return bufferedImage;
    }

    /**
     *
     * 基于单例模式获取 QRCode 对象
     * @author gclm
     * @date 2020/3/16 4:19 下午
     * @return: club.gclmit.chaos.core.helper.QRCodeHelper.QRCode
     * @throws
     */
    public static QRCodeHelper.QRCode getInstance(){
        return QRCodeHelper.QRCode.StaticQRCodeHolder.instance;
    }


    /**
     * <p>
     * 二维码生成器 参数
     * 注意事项：
     * 1. 二维码宽和高单位：像素
     * 2. 颜色采用16进制表示，和前端页面 CSS 的取色是一样的，注意前后景颜色应该对比明显，如常见的黑白
     * </p>
     *
     * @author: gclm
     * @date: 2019-08-19 10:09
     * @version: V1.0
     */
    public static class QRCode {

        private static class StaticQRCodeHolder {
            private static final QRCode instance = new QRCode();
        }

        private QRCode() {
        }

        /**
         * 二维码内容
         */
        private String content;

        /**
         * 生成类型，有 base64、file 二种方式
         * 默认为 base64
         */
        private String type = QRCodeConfig.IMAGE_TYPE_BASE64;

        /**
         * 当 generateType 为 file 时，geneartePath 为文件保存路径,默认为当前项目路径
         */
        private String geneartePath = System.getProperty("user.dir");

        /**
         * 二维码高，默认为400，单位：像素
         */
        private Integer height = 400;

        /**
         * 二维码宽，默认为400，单位：像素
         */
        private Integer width = 400;

        /**
         * 二维码前景色，默认为黑色（ 0x000000）
         */
        private Integer froutColor = 0x000000;

        /**
         * 背景色，默认为白色（ 0xFFFFFF）
         */
        private Integer backgroudColor = 0xFFFFFF;

        /**
         * 编码格式，默认为 UTF-8
         */
        private String encode = QRCodeConfig.IMAGE_CHARACTER_ENCODE;

        /**
         * BarcodeFormat ：设置编码类型，
         * 有条形码、二维码等，默认为二维码
         */
        private BarcodeFormat format = BarcodeFormat.QR_CODE;

        /**
         * ErrorCorrectionLevel 误差校正级别
         * L = ~7%
         * M = ~15%
         * Q = ~25%
         * H = ~30%
         * 默认为 L,级别越高贮存的信息越少，生成的图案不同，但扫描结果一样
         */
        private ErrorCorrectionLevel errorLevel = ErrorCorrectionLevel.M;

        /**
         * 二维码边框，单位为：像素，值越小，二维码距离四周越近
         */
        private Integer margin = 1;

        /**
         * 生成的图片格式
         */
        private String suffix = "png";


        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getGeneartePath() {
            return geneartePath;
        }

        public void setGeneartePath(String geneartePath) {
            this.geneartePath = geneartePath;
        }

        public Integer getHeight() {
            return height;
        }

        public void setHeight(Integer height) {
            this.height = height;
        }

        public Integer getWidth() {
            return width;
        }

        public void setWidth(Integer width) {
            this.width = width;
        }

        public Integer getFroutColor() {
            return froutColor;
        }

        public void setFroutColor(Integer froutColor) {
            this.froutColor = froutColor;
        }

        public Integer getBackgroudColor() {
            return backgroudColor;
        }

        public void setBackgroudColor(Integer backgroudColor) {
            this.backgroudColor = backgroudColor;
        }

        public String getEncode() {
            return encode;
        }

        public void setEncode(String encode) {
            this.encode = encode;
        }

        public BarcodeFormat getFormat() {
            return format;
        }

        public void setFormat(BarcodeFormat format) {
            this.format = format;
        }

        public ErrorCorrectionLevel getErrorLevel() {
            return errorLevel;
        }

        public void setErrorLevel(ErrorCorrectionLevel errorLevel) {
            this.errorLevel = errorLevel;
        }

        public Integer getMargin() {
            return margin;
        }

        public void setMargin(Integer margin) {
            this.margin = margin;
        }

        public String getSuffix() {
            return suffix;
        }

        public void setSuffix(String suffix) {
            this.suffix = suffix;
        }
    }
}
