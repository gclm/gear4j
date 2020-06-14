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

    public static final String IE = "msie";
    public static final String FIREFOX = "firefox";
    public static final String CHROME = "chrome";
    public static final String ANDROID = "Android";
    public static final String[] IOS_AGENTS = {"iphone", "ipad", "ipod"};
    public static final String[] MOBILE_AGENTS = {"iphone", "android", "ipad", "phone", "mobile", "wap", "netfront", "java", "opera mobi",
            "opera mini", "ucweb", "windows ce", "symbian", "series", "webos", "sony", "blackberry", "dopod",
            "nokia", "samsung", "palmsource", "xda", "pieplus", "meizu", "midp", "cldc", "motorola", "foma",
            "docomo", "up.browser", "up.link", "blazer", "helio", "hosin", "huawei", "novarra", "coolpad", "webos",
            "techfaith", "palmsource", "alcatel", "amoi", "ktouch", "nexian", "ericsson", "philips", "sagem",
            "wellcom", "bunjalloo", "maui", "smartphone", "iemobile", "spice", "bird", "zte-", "longcos",
            "pantech", "gionee", "portalmmm", "jig browser", "hiptop", "benq", "haier", "^lct", "320x320",
            "240x320", "176x220", "w3c ", "acs-", "alav", "alca", "amoi", "audi", "avan", "benq", "bird", "blac",
            "blaz", "brew", "cell", "cldc", "cmd-", "dang", "doco", "eric", "hipt", "inno", "ipaq", "java", "jigs",
            "kddi", "keji", "leno", "lg-c", "lg-d", "lg-g", "lge-", "maui", "maxo", "midp", "mits", "mmef", "mobi",
            "mot-", "moto", "mwbp", "nec-", "newt", "noki", "oper", "palm", "pana", "pant", "phil", "play", "port",
            "prox", "qwap", "sage", "sams", "sany", "sch-", "sec-", "send", "seri", "sgh-", "shar", "sie-", "siem",
            "smal", "smar", "sony", "sph-", "symb", "t-mo", "teli", "tim-", "tosh", "tsm-", "upg1", "upsi", "vk-v",
            "voda", "wap-", "wapa", "wapi", "wapp", "wapr", "webc", "winw", "winw", "xda", "xda-",
            "Googlebot-Mobile"};

    private UserAgentUtils() {
    }

    /**
     * 获取当前浏览器名称
     *
     * @param request HttpServletRequest
     * @return 返回浏览器名称
     */
    public static String getCurrentUserAgent(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        if (userAgent != null && !("".equals(userAgent.trim()))) {
            return userAgent;
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
        return getCurrentUserAgent(request).contains(IE);
    }

    /**
     * 是否是Firefox浏览器
     *
     * @param request HttpServletRequest 请求
     * @return boolean 如果是返回 true，否则返回 false
     */
    public static boolean isFirefox(HttpServletRequest request) {
        return getCurrentUserAgent(request).contains(FIREFOX);
    }

    /**
     * 是否是Chrome浏览器
     *
     * @param request HttpServletRequest 请求
     * @return boolean 如果是返回 true，否则返回 false
     */
    public static boolean isChrome(HttpServletRequest request) {
        return getCurrentUserAgent(request).contains(CHROME);
    }

    /**
     * 判断是否为手机端
     *
     * @param request HttpServletRequest 请求
     * @return boolean 如果是返回 true，否则返回 false
     * @author gclm
     */
    public static boolean isMoblie(HttpServletRequest request) {
        boolean flag = false;
        String userAgent = getCurrentUserAgent(request);
        for (String mobileAgent : MOBILE_AGENTS) {
            if (userAgent.toLowerCase().indexOf(mobileAgent) >= 0 &&
                    userAgent.toLowerCase().indexOf("windows nt") <= 0 &&
                    userAgent.toLowerCase().indexOf("macintosh") <= 0) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 判断是否为 Android
     *
     * @param request HttpServletRequest 请求
     * @return boolean 如果是返回 true，否则返回 false
     * @author gclm
     */
    public static boolean isAndroid(HttpServletRequest request) {
        return getCurrentUserAgent(request).contains(ANDROID);
    }

    /**
     * 判断是否为 IOS
     *
     * @param request HttpServletRequest 请求
     * @return boolean 如果是返回 true，否则返回 false
     * @author gclm
     */
    public static boolean isIos(HttpServletRequest request) {
        boolean flag = false;
        String userAgent = getCurrentUserAgent(request);
        for (String agent : IOS_AGENTS) {

            if (userAgent.contains(agent)) {
                flag = true;
            }
        }
        return flag;
    }
}
