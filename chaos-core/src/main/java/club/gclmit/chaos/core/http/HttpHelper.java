package club.gclmit.chaos.core.http;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Pattern;

/**
 * <p>
 *  IP 处理帮助类
 * </p>
 *
 * @author: gclm
 * @date: 2019-10-22 10:18:00
 * @version: V1.0
 */
public class HttpHelper {

    public static final Pattern IP_PATTERN = Pattern.compile("\\d{1,3}(\\.\\d{1,3}){3,5}$");

    private static final String ANYHOST = "0.0.0.0";
    private static final String LOCALHOST = "127.0.0.1";
    private static volatile InetAddress LOCAL_ADDRESS = null;

    /**
     * 获取本机的所有ipv4的ip
     *
     * @return 本机所有ipv4地址
     */
    public static List<String> getCurrentMachineIp() throws SocketException {
        List<String> resultList = new ArrayList<>();
        // 获得本机的所有网络接口
        Enumeration<NetworkInterface> nifs = NetworkInterface.getNetworkInterfaces();

        while (nifs.hasMoreElements()) {
            NetworkInterface nif = nifs.nextElement();

            // 获得与该网络接口绑定的 IP 地址，一般只有一个
            Enumeration<InetAddress> addresses = nif.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress addr = addresses.nextElement();

                // 只关心 IPv4 地址
                if (addr instanceof Inet4Address) {
                    resultList.add(addr.getHostAddress());
                }
            }
        }
        if (resultList.isEmpty()) {
            resultList.add(LOCALHOST);
        }
        return resultList;
    }

    /**
     * <p>
     *  getClientIp:获取访问用户的客户端IP（适用于公网与局域网）.
     * </p>
     *
     * @author 孤城落寞
     * @param: request
     * @date 2019/10/22 10:27
     * @return: java.lang.String
     * @throws
     */
    public static final String getClientIp(final HttpServletRequest request) {
        String ipString = request.getHeader("x-forwarded-for");
        if (StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {
            ipString = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {
            ipString = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ipString) || "unknown".equalsIgnoreCase(ipString)) {
            ipString = request.getRemoteAddr();
            /**
             * 根据网卡读取本机配置的IP
             */
            if(ipString.equals(LOCALHOST)){
                InetAddress inetAddress = null;
                try {
                    inetAddress = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipString = inetAddress.getHostAddress();
            }
        }

        // 多个路由时，取第一个非unknown的ip
        final String[] arr = ipString.split(",");
        for (final String str : arr) {
            if (!"unknown".equalsIgnoreCase(str)) {
                ipString = str;
                break;
            }
        }
        return ipString;
    }

    /**
     *  获取客户端请求方式
     *  eq:
     *   - ajax
     *   - form
     *   - websocket
     *
     * @author gclm
     * @param: request
     * @date 2020/1/11 9:35 下午
     * @return: java.lang.String
     * @throws
     */
    public static String getRequestType(HttpServletRequest request) {
        return request.getHeader("X-Requested-With");
    }

    /**
     *  获取Session Id
     *
     * @author gclm
     * @param: request
     * @date 2020/1/20 9:34 上午
     * @return: java.lang.String
     * @throws
     */
    public static String getSessionId(HttpServletRequest request) {
       return request.getSession().getId();
    }

    /**
     *  获取用户代理
     *
     * @author gclm
     * @param: request
     * @date 2020/1/20 10:38 上午
     * @return: java.lang.String
     * @throws
     */
    public static String getUserAgent(HttpServletRequest request) {
        return request.getHeader("User-Agent");
    }

}

