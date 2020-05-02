package club.gclmit.chaos.core.net;

import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * <p>
 * ip 工具类
 * </p>
 *
 * @author: gclm
 * @date: 2020/4/25 10:29 上午
 * @version: V1.0
 * @since 1.8
 */
public class IpUtils {

    private static final String LOCALHOST = "127.0.0.1";

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
}
