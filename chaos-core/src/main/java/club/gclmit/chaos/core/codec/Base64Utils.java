package club.gclmit.chaos.core.codec;

import java.util.Base64;
import lombok.experimental.UtilityClass;

/**
 * Base64 工具类
 *
 * @author gclm
 */
@UtilityClass
public class Base64Utils {

    /**
     * String To base64
     * @author gclm
     * @param encoder 使用哪个Base64
     * @param data    待转换数据
     * @return java.lang.String
     */
    public static byte[] decodeBase64(String data){
        return Base64.getDecoder().decode(data);
    }

    /**
     * byte[] To String
     * @author gclm
     * @param encoder 使用哪个Base64
     * @param data    待转换数据
     * @return java.lang.String
     */
    public static String encodeBase64String(Base64.Encoder encoder,byte[] data){
        return encoder.encodeToString(data);
    }

    /**
     * byte[] To String
     * @author gclm
     * @param data    待转换数据
     * @return java.lang.String
     */
    public static String encodeBase64String(byte[] data){
        return Base64.getEncoder().encodeToString(data);
    }

}
