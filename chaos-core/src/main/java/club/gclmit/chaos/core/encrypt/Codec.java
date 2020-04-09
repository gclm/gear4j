package club.gclmit.chaos.core.encrypt;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import club.gclmit.chaos.core.exception.ChaosCoreException;
import org.apache.commons.lang3.StringUtils;
import java.io.*;

/**
 * <p>
 * 所有加密接口的父类
 * </p>
 *
 * @author: gclm
 * @date: 2020/3/31 10:50 AM
 * @version: V1.0
 * @since 1.8
 */
public abstract class Codec {

    /**
     * 加密算法的核心，实现加密先集成这个接口
     *
     * @throws
     * @author gclm
     * @date 2020/3/31 2:51 PM
     */
    public abstract String encode(byte[] data);

    /**
     * 默认为 UTF-8的编码格式加密
     *
     * @throws
     * @author gclm
     * @param: str
     * @date 2020/3/31 2:52 PM
     * @return: java.lang.String
     */
    public String encode(String str) {
        return encode(str, null);
    }

    /**
     * 自定义编码格式加密
     *
     * @throws
     * @author gclm
     * @param: str     内容
     * @param: charset 编码格式
     * @date 2020/3/31 2:52 PM
     * @return: java.lang.String
     */
    public String encode(String str, String charset) {
        String result = null;
        if (StringUtils.isNotEmpty(charset)) {
            result = encode(str.getBytes(Charset.forName(charset)));
        } else {
            result = encode(str.getBytes(StandardCharsets.UTF_8));
        }
        return result;
    }

    /**
     * 文件加密
     *
     * @throws
     * @author gclm
     * @param: file
     * @date 2020/3/31 2:56 PM
     * @return: java.lang.String
     */
    public String encode(File file) {

        try {
            return encode(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new ChaosCoreException("当前文件不存在", e);
        }
    }

    /**
     * 输入流加密
     *
     * @throws
     * @author gclm
     * @param: in
     * @date 2020/3/31 2:56 PM
     * @return: java.lang.String
     */
    public String encode(InputStream in) {

        byte[] data = null;
        try {
            /**
             *  严谨起见,一定要加上这个判断,不要返回data[]长度为0的数组指针
             */
            if (in == null || in.available() == 0) {
                return null;
            }
            data = new byte[in.available()];
            in.read(data);
            in.close();
            return encode(data);
        } catch (IOException e) {
            throw new ChaosCoreException("readFileBytes, err", e);
        }
    }

}
