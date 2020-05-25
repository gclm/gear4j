package club.gclmit.chaos.core.lang.zxing;

/**
 * <p>
 * 条形码图片类型
 * </p>
 *
 * @author: gclm
 * @date: 2020/5/4 4:43 下午
 * @version: V1.0
 * @since 1.8
 */
public enum BarcodeImageType {

    /**
     * jpg 图片
     */
    JPG("jpg"),

    /**
     * png
     */
    PNG("png");

    private String code;

    BarcodeImageType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
