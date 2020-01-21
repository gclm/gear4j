package club.gclmit.chaos.helper.code;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

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
public class QRCode {

    public static QRCode getInstance(){
        return StaticQRCodeHolder.instance;
    }

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