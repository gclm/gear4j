package club.gclmit.chaos.core.util;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * user-agent 工具类
 * </p>
 *
 * @author: gclm
 * @date: 2020/4/13 10:05 上午
 * @version: V1.0
 * @since 1.8
 */
public class UserAgentUtils {

    private static final String IE = "msie";
    private static final String FIREFOX = "firefox";
    private static final String CHROME = "chrome";

    /**
     * 获取当前浏览器名称
     *
     * @param request
     * @return 返回浏览器名称
     */
    public static String getCurrent(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        if (userAgent != null && !("".equals(userAgent.trim()))) {
            if (userAgent.indexOf(CHROME) >= 0) {
                return CHROME;
            } else if (userAgent.indexOf(FIREFOX) >= 0) {
                return FIREFOX;
            } else if (userAgent.indexOf(IE) >= 0) {
                return IE;
            }
        }
        return null;
    }

    /**
     * 是否是IE浏览器
     *
     * @param request
     * @return
     */
    public static boolean isIe(HttpServletRequest request) {
        return IE.equals(getCurrent(request));
    }

    /**
     * 是否是Firefox浏览器
     *
     * @param request
     * @return
     */
    public static boolean isFirefox(HttpServletRequest request) {
        return FIREFOX.equals(getCurrent(request));
    }

    /**
     * 是否是Chrome浏览器
     *
     * @param request
     * @return
     */
    public static boolean isChrome(HttpServletRequest request) {
        return CHROME.equals(getCurrent(request));
    }
}
