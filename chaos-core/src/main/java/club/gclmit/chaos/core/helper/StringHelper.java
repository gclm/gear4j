package club.gclmit.chaos.core.helper;

import club.gclmit.chaos.core.exception.ChaosCoreException;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 * 字符串工具类
 * </p>
 *
 * @author: gclm
 * @date: 2020/3/27 2:37 下午
 * @version: V1.0
 * @since 1.8
 */
public class StringHelper {

    /**
     *  去除字符串所有空格
     * @author gclm
     * @param: content
     * @date 2020/3/27 2:39 下午
     * @return: java.lang.String
     * @throws
     */
    public static String notTrim(String content) {
        return content.replaceAll("\\s*", "");
    }

    /**
     *  获取输入流内容
     *
     * @author gclm
     * @param: stream
     * @date 2020/3/27 4:08 下午
     * @return: java.lang.String
     * @throws
     */
    public static String getInputStreamString(InputStream stream){
        StringBuilder result = new StringBuilder();
        try {
            IOUtils.readLines(stream, "UTF-8").forEach(result::append);
        } catch (IOException e) {
            throw new ChaosCoreException("获取输入流内容失败",e);
        }
        return result.toString();
    }
}
