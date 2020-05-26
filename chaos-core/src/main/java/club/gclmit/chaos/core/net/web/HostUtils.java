package club.gclmit.chaos.core.net.web;

import club.gclmit.chaos.core.exception.ChaosCoreException;

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
     *  获取主机名失败
     * </p>
     *
     * @author gclm
     * @return java.lang.String
     * @throws ChaosCoreException 封装自定义异常
     */
    public static String getHostName(){
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
           throw  new ChaosCoreException("获取主机名失败",e);
        }
    }

}
