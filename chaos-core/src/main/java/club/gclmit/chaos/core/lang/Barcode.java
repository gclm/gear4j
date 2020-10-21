package club.gclmit.chaos.core.lang;

import club.gclmit.chaos.core.exception.ChaosCoreException;
import club.gclmit.chaos.core.lang.text.Charsets;
import club.gclmit.chaos.core.lang.text.StringUtils;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;
import java.util.*;

/**
 * <p>
 * 二维码/条形码生成器
 * </p>
 *
 * @author gclm
 */
public class Barcode {

    /**
     * 默认编码格式
     */
    public static final String DEFAULT_CHARACTER_SET = Charsets.UTF_8;

    private static final MultiFormatWriter WRITER;

    static {
        WRITER = new MultiFormatWriter();
    }

    private Builder builder;

    public Barcode(Builder builder) {
        this.builder = builder;
    }

    /**
     *  是否为二维码并且嵌入 logo
     *
     * @author gclm
     * @return boolean
     */
    private boolean isQRCodeWithLogo(){
        return BarcodeFormat.QR_CODE == builder.format && Objects.nonNull(builder.logo);
    }

    /**
     *  生成代码源程序
     *
     * @author gclm
     * @return java.awt.image.BufferedImage
     */
    private BufferedImage generateOrigin() throws WriterException {
        BitMatrix matrix = WRITER.encode(builder.content, builder.format, builder.width, builder.height, builder.hists);

        // 使BufferedImage勾画QRCode  (matrixWidth 是行二维码像素点)
        int matrixWidth = matrix.getWidth();
        //新建一个内存缓存图片----图片大小为qrCodeSize-200(注：byteMatrix会存在一些白色边框，根据生成图片的大小白色边框大小不一样)
        BufferedImage image = new BufferedImage(matrixWidth - 200, matrixWidth - 200, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        graphics.fillRect(0, 0, matrixWidth, matrixWidth); //填充白色在图像上
        // 使用比特矩阵画并保存图像
        graphics.setColor(Color.BLACK);
        for (int i = 0; i < matrixWidth; i++) {
            for (int j = 0; j < matrixWidth; j++) {
                if (matrix.get(i, j)) {
                    //去掉边框
                    graphics.fillRect(i - 100, j - 100, 1, 1); //画一个黑色像素
                }
            }
        }

        if (Objects.nonNull(builder.matrixToImageConfig)) {
            return MatrixToImageWriter.toBufferedImage(matrix,builder.matrixToImageConfig);
        }
        return MatrixToImageWriter.toBufferedImage(matrix);
    }

    /**
     *  生成带 logo 的 BufferedImage
     *
     * @author gclm
     * @param logo    嵌入的logo
     * @return java.awt.image.BufferedImage
     */
    private BufferedImage generateWithLogo(BufferedImage logo) throws WriterException {

//        BufferedImage result = new BufferedImage(image.getWidth(),image.getHeight(),BufferedImage.TYPE_INT_ARGB);
//        Graphics2D graphics2D = result.createGraphics();
//        graphics2D.drawImage(image,0,0,null);
//        image.flush();
//        // 抗锯齿
//        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
//
//        /**
//         * 设置logo的大小，最多 20%
//         */
//        int loginWidth  = logo.getWidth(null)  > result.getWidth() * 2/10  ?  (result.getWidth() * 2/10)  : logo.getWidth(null);
//        int loginHeight = logo.getHeight(null) > result.getHeight() * 2/10 ?  (result.getHeight() * 2/10) : logo.getHeight(null);
//
//        /**
//         * 计算图片放置位置，默认在中间
//         */
//        int x = (result.getWidth() - loginWidth) / 2;
//        int y = (result.getHeight() - loginHeight) / 2;
//
//        /**
//         * 开始绘制图片
//         */
//        graphics2D.drawImage(logo,x,y,loginWidth,loginHeight,null);
//        logo.flush();
//        graphics2D.drawRoundRect(x,y,loginWidth,loginHeight,15,15);
//        graphics2D.dispose();
//        result.flush();
//
//        return result;

        BufferedImage image = generateOrigin();

        /**
         * 重新设置logo的大小,设置为二维码图片的20%,因为过大会盖掉二维码  
         */
        int widthLogo =  logo.getWidth(null)>image.getWidth()*2/10? (image.getWidth()*2/10) : logo.getWidth(null);
        int heightLogo = logo.getHeight(null)>image.getHeight()*2/10? (image.getHeight()*2/10) : logo.getHeight(null);


        // 计算logo图片放置位置---logo放在中心  
        int x = (image.getWidth() - widthLogo) / 2;
        int y = (image.getHeight() - heightLogo) / 2;

        Graphics2D graphics = image.createGraphics();

        //开始绘制Logo到二维码图片上  
        graphics.drawImage(logo, x, y, widthLogo, heightLogo, null);
        graphics.drawRoundRect(x, y, widthLogo, heightLogo, 15, 15);
        graphics.drawRect(x, y, widthLogo, heightLogo);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.dispose();
        logo.flush();
        image.flush();
        return image;
    }

    /**
     *  生成 BufferedImage
     *
     * @author gclm
     * @return java.awt.image.BufferedImage
     */
    public BufferedImage generate(){
        try {
            if (isQRCodeWithLogo()){
                return generateWithLogo(builder.logo);
            }
            return generateOrigin();
        } catch (Exception e) {
            throw new ChaosCoreException("二维码生成失败",e);
        }
    }

    /**
     * 生成指定类型图片输出至输出流
     *
     * @author gclm
     * @param format  图片类型
     * @param stream  输出流
     */
    public void generate(BarcodeImageType format, OutputStream stream){
        try {
            if (isQRCodeWithLogo()){
                ImageIO.write(generateWithLogo(builder.logo),format.getCode(),stream);
            } else {
                ImageIO.write(generateOrigin(),format.getCode(),stream);
            }
        } catch (Exception e) {
            throw new ChaosCoreException("二维码生成失败",e);
        }
    }

    /**
     * 生成指定类型图片的字节数组
     *
     * @author gclm
     * @param format  图片类型
     * @return byte[] 字节数组
     */
    public byte[] generate(BarcodeImageType format){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        generate(format,stream);
        return stream.toByteArray();
    }

    /**
     * 生成指定类型图片的字符串
     *
     * @author gclm
     * @param format  图片类型
     * @param flag    是否进行base64转码，true: 转码 false: 不转码
     * @return String
     */
    public String generate(BarcodeImageType format,boolean flag){
        byte[] bytes = generate(format);
        if (flag){
            return Base64.getEncoder().encodeToString(bytes).trim();
        }
        return StringUtils.toString(bytes, Charsets.UTF_8);
    }

    /**
     * 生成指定类型图片输出至指定文件
     *
     * @author gclm
     * @param format  图片类型
     * @param file    指定文件
     * @return String
     */
    public String generate(BarcodeImageType format,File file){
        try {
            generate(format,new FileOutputStream(file));
            return file.getAbsolutePath();
        } catch (Exception e){
            throw new ChaosCoreException("二维码生成失败",e);
        }
    }

    /**
     * 生成指定类型图片输出至指定文件
     *
     * @author gclm
     * @param format  图片类型
     * @param path    文件路径
     */
    public void generate(BarcodeImageType format,Path path){
        generate(format,path.toFile());
    }

    public static Builder of(){
        return new Builder();
    }

    // 解码
    //=============================================================================

    /**
     * <p>
     *  解析二维码
     * </p>
     *
     * @author gclm
     * @param image 二维码
     * @return club.gclmit.chaos.core.lang.Barcode.Decoder
     */
    public static Decoder from(BufferedImage image){
        return new Decoder(image);
    }

    /**
     * <p>
     *  解析二维码
     * </p>
     *
     * @author gclm
     * @param stream 二维码
     * @return club.gclmit.chaos.core.lang.Barcode.Decoder
     * @throws IOException if an I/O error occurs
     */
    public static Decoder from(InputStream stream) throws IOException {
        return new Decoder(ImageIO.read(stream));
    }

    /**
     * <p>
     *  解析二维码
     * </p>
     *
     * @author gclm
     * @param file 二维码
     * @return club.gclmit.chaos.core.lang.Barcode.Decoder
     * @throws IOException if an I/O error occurs
     */
    public static Decoder from(File file) throws IOException {
        return new Decoder(ImageIO.read(file));
    }

    /**
     * <p>
     *  解析二维码
     * </p>
     *
     * @author gclm
     * @param path 二维码
     * @return club.gclmit.chaos.core.lang.Barcode.Decoder
     * @throws IOException if an I/O error occurs
     */
    public static Decoder from(Path path) throws IOException {
        return from(path.toFile());
    }

    /**
     * <p>
     *  解析二维码
     * </p>
     *
     * @author gclm
     * @param url 二维码
     * @return club.gclmit.chaos.core.lang.Barcode.Decoder
     * @throws IOException if an I/O error occurs
     */
    public static Decoder from(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty(
                "User-Agent",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.90 Safari/537.36");
        return new Decoder(ImageIO.read(connection.getInputStream()));
    }

    /**
     * <p>
     *  解析二维码
     * </p>
     *
     * @author gclm
     * @param bytes 二维码
     * @return club.gclmit.chaos.core.lang.Barcode.Decoder
     * @throws IOException if an I/O error occurs
     */
    public static Decoder from(byte[] bytes) throws IOException {
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

        private BufferedImage image;

        private Decoder(BufferedImage image){
            this.image = image;
        }

        public Result decode() {
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
            Hashtable hints = new Hashtable();
            hints.put(DecodeHintType.CHARACTER_SET, DEFAULT_CHARACTER_SET);

            /**
             * 如果图片不是二维码图片，则 decode 抛出异常 com.google.zxing.NotFoundException
             * MultiFormatWriter 的 encode 用于对内容进行编码成 2D 矩阵
             * MultiFormatReader 的 decode 用于读取二进制位图数据
             */
            try {
                Result result = READER.decode(binaryBitmap, hints);
                READER.reset();
                return result;
            } catch (NotFoundException e) {
                throw new ChaosCoreException("该图片不是二维码图片", e);
            }
        }
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
            hists.put(EncodeHintType.CHARACTER_SET, DEFAULT_CHARACTER_SET);
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
        public Barcode format(BarcodeFormat format){
            this.format = format;
            return new Barcode(this);
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
