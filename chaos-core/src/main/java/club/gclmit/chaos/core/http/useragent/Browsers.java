package club.gclmit.chaos.core.http.useragent;

/**
 * 浏览器类型枚举
 *
 * @author gclm
 * @since 11
 */
public enum Browsers {
    /**
     * IE 浏览器
     */
    IE("msie"),
    /**
     * 谷歌浏览器
     */
    CHROME("chrome"),
    /**
     * 火狐浏览器
     */
    FIREFOX("firefox"),
    /**
     * opera 浏览器
     */
    OPERA("opera"),
    /**
     * safari 浏览器
     */
    SAFARI("safari");

    /**
     * 浏览器状态码
     */
    private String code;

    Browsers(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
