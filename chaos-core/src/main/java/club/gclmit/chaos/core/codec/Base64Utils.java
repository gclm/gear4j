package club.gclmit.chaos.core.codec;

import lombok.experimental.UtilityClass;

import java.util.*;

/**
 * Base64 工具类
 *
 * @author gclm
 */
@UtilityClass
public class Base64Utils {

    /**
     * 获取Base64编码的图片前缀
     *
     * @return Base64图片前缀集合
     * @author gclm
     */
    public static List<String> getBase64ImagePrefix() {
        List<String> list = new ArrayList<>(4);
        list.add("data:image/gif;base64,");
        list.add("data:image/png;base64,");
        list.add("data:image/jpeg;base64,");
        list.add("data:image/x-icon;base64,");
        return list;
    }

    /**
     * 获取Base64编码的前缀
     *
     * @return Base64编码前缀集合
     * @author gclm
     */
    public static List<String> getBase64Prefix() {

        List<String> list = new ArrayList<>(8);
        list.add("data:text/html;base64,");
        list.add("data:text/css;base64,");
        list.add("data:text/javascript;base64,");
        list.add("data:image/gif;base64,");
        list.add("data:image/png;base64,");
        list.add("data:image/jpeg;base64,");
        list.add("data:image/x-icon;base64,");
        return list;
    }

    /**
     *
     * 获取 Data URL scheme
     * data:,文本数据
     * data:text/plain,文本数据
     * data:text/html,HTML代码
     * data:text/html;base64,base64编码的HTML代码
     * data:text/css,CSS代码
     * data:text/css;base64,base64编码的CSS代码
     * data:text/JavaScript,Javascript代码
     * data:text/javascript;base64,base64编码的Javascript代码
     * data:image/gif;base64,base64编码的gif图片数据
     * data:image/png;base64,base64编码的png图片数据
     * data:image/jpeg;base64,base64编码的jpeg图片数据
     * data:image/x-icon;base64,base64编码的icon图片数据
     *
     * @author gclm
     * @return UrlScheme 集合
     */
    public static List<String> getDataUrlScheme() {

        List<String> list = new ArrayList<>(12);
        list.add("data:,");
        list.add("data:text/plain,");
        list.add("data:text/html,");
        list.add("data:text/html;base64,");
        list.add("data:text/css,");
        list.add("data:text/css;base64,");
        list.add("data:text/JavaScript,");
        list.add("data:text/javascript;base64,");
        list.add("data:image/gif;base64,");
        list.add("data:image/png;base64,");
        list.add("data:image/jpeg;base64,");
        list.add("data:image/x-icon;base64,");
        return list;
    }


}
