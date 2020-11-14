package club.gclmit.chaos.web.util;

import club.gclmit.chaos.core.exception.ChaosException;

import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * <p>
 * Host 本机工具类
 * </p>
 *
 * @author gclm
 */
public class HostUtils {

    private static final String LOCALHOST = "127.0.0.1";

    private HostUtils() {
    }

    /**
     * 获取本机的所有ipv4的ip
     *
     * @return 本机所有ipv4地址
     * @throws SocketException Socket连接异常
     */
    public static List<String> getHostIp() throws SocketException {
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
     * 获取主机名失败
     * </p>
     *
     * @return java.lang.String
     * @throws ChaosException 封装自定义异常
     * @author gclm
     */
    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            throw new ChaosException("获取主机名失败", e);
        }
    }

}
