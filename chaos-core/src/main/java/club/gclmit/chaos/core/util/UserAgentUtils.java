package club.gclmit.chaos.core.util;

/**
 * UserAgent 工具类
 *
 * @author gclm
 */
public class UserAgentUtils {

//    public static Map<String,String> getWindowsOsUserAgent(){
//        Map<String,String> windows_os = new HashMap<>(20);
//        windows_os.put("Windows NT 10.0","Windows 10");
//        windows_os.put("Windows NT 6.2","Windows 8");
//        windows_os.put("Windows NT 6.1","");
//        windows_os.put("Windows NT 6.0","");
//        windows_os.put("Windows NT 5.2","");
//        windows_os.put("Windows NT 5.1","");
//        windows_os.put("Windows NT 5.01","");
//        windows_os.put("Windows NT 5.0","");
//        windows_os.put("Windows 98; Win 9x 4.90","");
//        windows_os.put("Windows 98","");
//        windows_os.put("Windows 95","");
//        windows_os.put("Windows CE","");
//    }

    public static String getOs(String userAgent) {
        if (userAgent.length() < 4) {
            return "其他系统";
        }
        if (userAgent.contains("Windows")) {
            if (userAgent.contains("Windows NT 10.0")) {
                return "Windows 10";
            } else if (userAgent.contains("Windows NT 6.2")) {
                return "Windows 8";
            } else if (userAgent.contains("Windows NT 6.1")) {
                return "Windows 7";
            } else if (userAgent.contains("Windows NT 6.0")) {
                return "Windows Vista";
            } else if (userAgent.contains("Windows NT 5.2")) {
                return "Windows XP";
            } else if (userAgent.contains("Windows NT 5.1")) {
                return "Windows XP";
            } else if (userAgent.contains("Windows NT 5.01")) {
                return "Windows 2000";
            } else if (userAgent.contains("Windows NT 5.0")) {
                return "Windows 2000";
            } else if (userAgent.contains("Windows NT 4.0")) {
                return "Windows NT 4.0";
            } else if (userAgent.contains("Windows 98; Win 9x 4.90")) {
                return "Windows ME";
            } else if (userAgent.contains("Windows 98")) {
                return "Windows 98";
            } else if (userAgent.contains("Windows 95")) {
                return "Windows 95";
            } else if (userAgent.contains("Windows CE")) {
                return "Windows CE";
            }
        } else if (userAgent.contains("Mac OS X")) {
            if (userAgent.contains("iPhone")) {
                return "iPhone";
            } else if (userAgent.contains("iPad")) {
                return "iPad";//判断系统
            } else {
                return "Mac";//判断系统
            }
        } else if (userAgent.contains("Android")) {
            return "Android";//判断系统
        } else if (userAgent.contains("Linux")) {
            return "Linux";//判断系统
        } else if (userAgent.contains("FreeBSD")) {
            return "FreeBSD";//判断系统
        } else if (userAgent.contains("Solaris")) {
            return "Solaris";//判断系统
        }
        return "其他系统";
    }

    public static String getBrowser(String userAgent) {
        if (userAgent.length() < 4) {
            return "其他浏览器";
        }
        if (userAgent.contains("Edge")) {
            return "Microsoft Edge";
        } else if (userAgent.contains("QQBrowser")) {
            return "QQ浏览器";
        } else if (userAgent.contains("Chrome") && userAgent.contains("Safari")) {
            return "Chrome";
        } else if (userAgent.contains("Firefox")) {
            return "Firefox";
        } else if (userAgent.contains("360")) {//Internet Explorer 6
            return "360浏览器";
        } else if (userAgent.contains("Opera")) {//Internet Explorer 6
            return "Opera";
        } else if (userAgent.contains("Safari") && !userAgent.contains("Chrome")) {
            return "Safari";
        } else if (userAgent.contains("Sogou")) {//Internet Explorer 6
            return "搜狗浏览器";
        } else if (userAgent.contains("TencentTraveler")) {//Internet Explorer 6
            return "腾讯浏览器";
        } else if (userAgent.contains("UC")) {//Internet Explorer 6
            return "UC浏览器";
        } else if (userAgent.contains("MicroMessenger/7")) {//Internet Explorer 6
            return "微信访问";
        } else if (userAgent.contains("TIM")) {//Internet Explorer 6
            return "TIM访问";
        } else if (userAgent.contains("QQ")) {//Internet Explorer 6
            return "QQ访问";
        } else if (userAgent.contains("Maxthon")) {//Internet Explorer 6
            return "遨游浏览器";
        } else if (userAgent.contains("Quark")) {//Internet Explorer 6
            return "夸克浏览器";
        } else if (userAgent.contains("Build")) {//Internet Explorer 6
            return "百度浏览器";
        } else if (userAgent.contains("2345")) {//Internet Explorer 6
            return "2345浏览器";
        } else if (userAgent.contains("Zeus")) {//Internet Explorer 6
            return "宙斯浏览器";
        } else if (userAgent.contains("LieBao")) {//Internet Explorer 6
            return "猎豹浏览器";
        } else if (userAgent.contains("Vivo")) {//Internet Explorer 6
            return "vivo浏览器";
        } else if (userAgent.contains("Tao")) {//Internet Explorer 6
            return "淘宝浏览器";
        } else if (userAgent.contains("Vivo")) {//Internet Explorer 6
            return "vivo浏览器";
        } else if (userAgent.contains("MSIE")) {
            if (userAgent.contains("MSIE 10.0")) {//Internet Explorer 10
                return "IE 10";
            } else if (userAgent.contains("MSIE 9.0")) {//Internet Explorer 9
                return "IE 9";
            } else if (userAgent.contains("MSIE 8.0")) {//Internet Explorer 8
                return "IE 8";
            } else if (userAgent.contains("MSIE 7.0")) {//Internet Explorer 7
                return "IE 7";
            } else if (userAgent.contains("MSIE 6.0")) {//Internet Explorer 6
                return "IE 6";
            }
        }
        return "其他浏览器";
    }
}
