package club.gclmit.chaos.core.util;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * user-agent 工具类
 * </p>
 *
 * @author gclm
 */
public class UserAgentUtils {

    private static final String IE = "msie";
    private static final String FIREFOX = "firefox";
    private static final String CHROME = "chrome";

    /**
     * 获取当前浏览器名称
     *
     * @param request  HttpServletRequest
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
     * @param request HttpServletRequest 请求
     * @return boolean 如果是返回 true，否则返回 false
     */
    public static boolean isIe(HttpServletRequest request) {
        return IE.equals(getCurrent(request));
    }

    /**
     * 是否是Firefox浏览器
     *
     * @param request HttpServletRequest 请求
     * @return boolean 如果是返回 true，否则返回 false
     */
    public static boolean isFirefox(HttpServletRequest request) {
        return FIREFOX.equals(getCurrent(request));
    }

    /**
     * 是否是Chrome浏览器
     *
     * @param request HttpServletRequest 请求
     * @return boolean 如果是返回 true，否则返回 false
     */
    public static boolean isChrome(HttpServletRequest request) {
        return CHROME.equals(getCurrent(request));
    }
}
