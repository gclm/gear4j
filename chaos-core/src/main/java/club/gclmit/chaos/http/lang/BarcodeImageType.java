package club.gclmit.chaos.http.lang;

/**
 * <p>
 * 条形码图片类型
 * </p>
 *
 * @author gclm
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
