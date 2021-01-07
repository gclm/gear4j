package club.gclmit.chaos.core.lang.qrcode;

import club.gclmit.chaos.core.exception.ChaosException;
import club.gclmit.chaos.core.io.FileUtils;
import club.gclmit.chaos.core.lang.Barcode;
import club.gclmit.chaos.core.lang.BarcodeImageType;
import club.gclmit.chaos.core.util.StringUtils;
import cn.hutool.core.util.CharsetUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 生成二维码
 *
 * @author gclm
 */
public class GenerateQrCode {

    private GenerateQrCode() {
    }

    private static class GenerateQrCodeHolder {
        private static final GenerateQrCode INSTANCE = new GenerateQrCode();
    }

    /**
     * 单例模式
     *
     * @author gclm
     * @return club.gclmit.chaos.core.lang.qrcode.GenerateQrCode
     */
    public static GenerateQrCode getInstance() {
        return GenerateQrCodeHolder.INSTANCE;
    }


    public static Builder of(){
        return new Builder();
    }

    private static BufferedImage toBufferedImage(QrCodeOption option) throws WriterException, IOException {
        BitMatrix bitMatrix = QrCodeUtil.encode(qrCodeConfig);
        return QrCodeUtil.toBufferedImage(qrCodeConfig, bitMatrix);
    }

    private static String toString(QrCodeOption option) throws WriterException, IOException {
        BufferedImage bufferedImage = toBufferedImage(option);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, qrCodeOptions.getPicType(), outputStream);
        return Base64Util.encode(outputStream);
    }

    private static boolean toFile(QrCodeOption qrCodeConfig, String filePath) throws WriterException, IOException {
        File file = new File(filePath);
        FileUtils.mkDir(file);

        BufferedImage bufferedImage = asBufferedImage(qrCodeConfig);
        if (!ImageIO.write(bufferedImage, qrCodeConfig.getPicType(), file)) {
            throw new IOException("save qrcode image error!");
        }

        return true;
    }

    /**
     * 生成条形码
     */
    public static class Builder {

        /**
         * 二维码内容
         */
        private String content;

        /**
         * BarcodeFormat ：设置编码类型，
         * 有条形码、二维码等，默认为二维码
         */
        private BarcodeFormat format;

        /**
         * 二维码高，默认为200，单位：像素
         */
        private Integer height = 200;

        /**
         * 二维码宽，默认为200，单位：像素
         */
        private Integer width = 200;

        /**
         *  EncodeHintType：编码提示类型配置
         */
        private Map<EncodeHintType, Object> hists;

        /**
         * 图片logo
         */
        private BufferedImage logo;

        /**
         * logo 占二维码的比例
         */
        private Integer rate = 20;

        /**
         * 生成图片类型
         */
        private BarcodeImageType type = BarcodeImageType.JPG;

        /**
         * 条形码颜色
         */
        private MatrixToImageConfig matrixToImageConfig;


        public Builder() {
            initDefaultHints();
        }

        /**
         *  初始化默认编码配置
         * com.google.zxing.EncodeHintType 编码提示类型，枚举类型
         *  EncodeHintType.CHARACTER_SET 设置字符编码类型
         *  EncodeHintType.ERROR_CORRECTION 误差校正级别，详情看 QrCode 的 errorLevel 属性
         *  EncodeHintType.MARGIN 设置二维码边框，详情看 QrCode 的 margin 属性
         */
        private void initDefaultHints(){
            hists = new HashMap<>(10);
            hists.put(EncodeHintType.CHARACTER_SET, QrCode.DEFAULT_CHARACTER_SET);
            /**
             * 二维码边框，单位为：像素，值越小，二维码距离四周越近
             */
            hists.put(EncodeHintType.MARGIN, 1);
            /**
             * ErrorCorrectionLevel 误差校正级别
             * L = ~7%
             * M = ~15%
             * Q = ~25%
             * H = ~30%
             * 默认为 L,级别越高贮存的信息越少，生成的图案不同，但扫描结果一样
             */
            hists.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        }

        /**
         *  设置logo占二维码比例
         *
         * @author gclm
         * @param rate  比例
         * @return club.gclmit.chaos.core.lang.Barcode.Builder
         */
        public Builder rate(Integer rate){
            this.rate = rate;
            return this;
        }

        /**
         *  设置条形码宽高
         *
         * @author gclm
         * @param width        图片宽度
         * @param height       图片高度
         * @return club.gclmit.chaos.core.lang.Barcode.Builder
         */
        public Builder size(Integer width, Integer height) {
            this.height = height;
            this.width = width;
            return this;
        }

        /**
         *  设置条形码的尺寸
         *
         * @author gclm
         * @param size         图片尺寸（size=width=height）
         * @return club.gclmit.chaos.core.lang.Barcode.Builder
         */
        public Builder size(Integer size) {
            return size(size,size);
        }

        /**
         *  设置条形码的颜色
         *
         * @author gclm
         * @param onColor     前景色 ARGB
         * @param offColor    背景色 ARGB
         * @return club.gclmit.chaos.core.lang.Barcode.Builder
         */
        public Builder color(Integer onColor, Integer offColor) {
            this.matrixToImageConfig = new MatrixToImageConfig(onColor, offColor);
            return this;
        }

        /**
         *  设置条形码内容
         *
         * @author gclm
         * @param content 条形码内容
         * @return club.gclmit.chaos.core.lang.Barcode.Builder
         */
        public Builder content(String content){
            this.content = content;
            return this;
        }

        /**
         *  设置 EncodeHintType
         *
         * @author gclm
         * @param hists EncodeHintType
         * @return club.gclmit.chaos.core.lang.Barcode.Builder
         */
        public Builder hints(Map<EncodeHintType, Object> hists){
            this.hists.putAll(hists);
            return this;
        }

        /**
         *  设置生成类型(生成二维码还是条形码)
         *
         * @author gclm
         * @param format 生成类型(生成二维码还是条形码)
         * @return club.gclmit.chaos.core.lang.Barcode.Builder
         */
        public GenerateQrCode format(BarcodeFormat format){
            this.format = format;
            return new GenerateQrCode(this);
        }

        /**
         *  二维码
         *
         * @author gclm
         * @return club.gclmit.chaos.core.lang.Barcode.Builder
         */
        public Barcode qrCode(){
            return format(BarcodeFormat.QR_CODE);
        }

        /**
         *  二维码
         * @param logo 二维码logo
         * @author gclm
         * @return club.gclmit.chaos.core.lang.Barcode.Builder
         */
        public Barcode qrCode(BufferedImage logo){
            this.logo = logo;
            return format(BarcodeFormat.QR_CODE);
        }

        /**
         *  二维码
         * @param logo 二维码logo
         * @author gclm
         * @return club.gclmit.chaos.core.lang.Barcode.Builder
         * @throws IOException if an I/O error occurs
         *
         */
        public Barcode qrCode(URL logo) throws IOException {
            return qrCode(ImageIO.read(logo));
        }

        /**
         *  二维码
         * @param logo 二维码logo
         * @author gclm
         * @return club.gclmit.chaos.core.lang.Barcode.Builder
         * @throws IOException if an I/O error occurs
         */
        public Barcode qrCode(InputStream logo) throws IOException {
            return qrCode(ImageIO.read(logo));
        }

        /**
         *  二维码
         * @param logo 二维码logo
         * @author gclm
         * @return club.gclmit.chaos.core.lang.Barcode.Builder
         * @throws IOException if an I/O error occurs
         */
        public Barcode qrCode(File logo) throws IOException {
            return qrCode(ImageIO.read(logo));
        }

        /**
         *  二维码
         * @param logo 二维码logo
         * @author gclm
         * @return club.gclmit.chaos.core.lang.Barcode.Builder
         * @throws IOException if an I/O error occurs
         */
        public Barcode qrCode(Path logo) throws IOException {
            return qrCode(ImageIO.read(logo.toFile()));
        }
    }
}
