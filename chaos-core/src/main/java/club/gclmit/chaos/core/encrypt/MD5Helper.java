package club.gclmit.chaos.core.encrypt;

import club.gclmit.chaos.core.exception.ChaosCoreException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <p>
 *  MD5工具类
 * </p>
 *
 * @author: gclm
 * @date: 2020/3/30 7:57 PM
 * @version: V1.0
 * @since 1.8
 */
public class MD5Helper extends Codec{

    @Override
    public String encode(byte[] data) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(data);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new ChaosCoreException("转换 md5 签名异常",e);
        }
    }
}
