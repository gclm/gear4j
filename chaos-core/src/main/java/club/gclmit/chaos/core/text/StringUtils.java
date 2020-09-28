package club.gclmit.chaos.core.text;

import club.gclmit.chaos.core.collection.ArrayUtils;
import club.gclmit.chaos.core.io.IOUtils;
import club.gclmit.chaos.core.util.ObjectUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * <p>
 *  String 工具类
 * </p>
 *
 * @author gclm
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    public static final char SLASH = '/';

    public static final char BACKSLASH = '\\';

    public static final char DELIM_START = '{';

    public static final char DELIM_END = '}';

    public static final String NULL = "null";

    public static final String EMPTY_JSON = "{}";


    /**
     * 去除字符串中指定的多个字符，如有多个则全部去除
     *
     * @param str   字符串
     * @param chars 字符列表
     * @return 去除后的字符
     * @since 4.2.2
     */
    public static String removeAll(CharSequence str, char... chars) {
        if (null == str || ArrayUtils.isEmpty(chars)) {
            return toString(str);
        }
        final int len = str.length();
        if (0 == len) {
            return toString(str);
        }

        final StringBuilder builder = builder(len);
        char c;
        for (int i = 0; i < len; i++) {
            c = str.charAt(i);
            if (false == ArrayUtils.contains(chars, c)) {
                builder.append(c);
            }
        }
        return builder.toString();
    }

    // StringBuilder
    //-----------------------------------------------------------------------

    /**
     * 创建StringBuilder对象
     *
     * @return StringBuilder对象
     */
    public static StringBuilder builder() {
        return new StringBuilder();
    }

    /**
     * 创建StringBuilder对象
     *
     * @param capacity 初始大小
     * @return StringBuilder对象
     */
    public static StringBuilder builder(int capacity) {
        return new StringBuilder(capacity);
    }

    /**
     * 创建StringBuilder对象
     *
     * @param strs 初始字符串列表
     * @return StringBuilder对象
     */
    public static StringBuilder builder(CharSequence... strs) {
        final StringBuilder sb = new StringBuilder();
        for (CharSequence str : strs) {
            sb.append(str);
        }
        return sb;
    }


    // UUID
    //-----------------------------------------------------------------------

    /**
     * All possible chars for representing a number as a String
     */
    public static final byte[] DIGITS = {
            '0' , '1' , '2' , '3' , '4' , '5' ,
            '6' , '7' , '8' , '9' , 'a' , 'b' ,
            'c' , 'd' , 'e' , 'f' , 'g' , 'h' ,
            'i' , 'j' , 'k' , 'l' , 'm' , 'n' ,
            'o' , 'p' , 'q' , 'r' , 's' , 't' ,
            'u' , 'v' , 'w' , 'x' , 'y' , 'z' ,
            'A' , 'B' , 'C' , 'D' , 'E' , 'F' ,
            'G' , 'H' , 'I' , 'J' , 'K' , 'L' ,
            'M' , 'N' , 'O' , 'P' , 'Q' , 'R' ,
            'S' , 'T' , 'U' , 'V' , 'W' , 'X' ,
            'Y' , 'Z'
    };



    /**
     * 生成uuid，采用 jdk 9 的形式，优化性能
     *
     * @return UUID
     */
    public static String getUUID() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        long lsb = random.nextLong();
        long msb = random.nextLong();
        byte[] buf = new byte[32];
        formatUnsignedLong(lsb, buf, 20, 12);
        formatUnsignedLong(lsb >>> 48, buf, 16, 4);
        formatUnsignedLong(msb, buf, 12, 4);
        formatUnsignedLong(msb >>> 16, buf, 8, 4);
        formatUnsignedLong(msb >>> 32, buf, 0, 8);
        return new String(buf, CharsetUtils.CHARSET_UTF_8);
    }

    private static void formatUnsignedLong(long val, byte[] buf, int offset, int len) {
        int charPos = offset + len;
        int radix = 1 << 4;
        int mask = radix - 1;
        do {
            buf[--charPos] = DIGITS[((int) val) & mask];
            val >>>= 4;
        } while (charPos > offset);
    }

    // Format
    //-----------------------------------------------------------------------

    /**
     * 有序的格式化文本，使用{number}做为占位符<br>
     * 例：<br>
     * 通常使用：format("this is {0} for {1}", "a", "b") =》 this is a for b<br>
     *
     * @param pattern   文本格式
     * @param arguments 参数
     * @return 格式化后的文本
     */
    public static String indexedFormat(CharSequence pattern, Object... arguments) {
        return MessageFormat.format(pattern.toString(), arguments);
    }

    /**
     * 格式化文本，使用 {varName} 占位<br>
     * map = {a: "aValue", b: "bValue"} format("{a} and {b}", map) ---=》 aValue and bValue
     *
     * @param template 文本模板，被替换的部分用 {key} 表示
     * @param map      参数值对
     * @return 格式化后的文本
     */
    public static String format(CharSequence template, Map<?, ?> map) {
        if (null == template) {
            return null;
        }
        if (null == map || map.isEmpty()) {
            return template.toString();
        }

        String template2 = template.toString();
        String value;
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            value = toString(entry.getValue());
            if (null != value) {
                template2 = replace(template2, "{" + entry.getKey() + "}", value);
            }
        }
        return template2;
    }

    /**
     * 格式化文本, {} 表示占位符<br>
     * 此方法只是简单将占位符 {} 按照顺序替换为参数<br>
     * 如果想输出 {} 使用 \\转义 { 即可，如果想输出 {} 之前的 \ 使用双转义符 \\\\ 即可<br>
     * 例：<br>
     * 通常使用：format("this is {} for {}", "a", "b") =》 this is a for b<br>
     * 转义{}： format("this is \\{} for {}", "a", "b") =》 this is \{} for a<br>
     * 转义\： format("this is \\\\{} for {}", "a", "b") =》 this is \a for b<br>
     *
     * @param template 文本模板，被替换的部分用 {} 表示
     * @param params   参数值
     * @return 格式化后的文本
     */
    public static String format(CharSequence template, Object... params) {
        if (null == template) {
            return null;
        }
        if (ArrayUtils.isEmpty(params) || isBlank(template)) {
            return template.toString();
        }
        return format(template.toString(), params);
    }

    /**
     * 格式化字符串<br>
     * 此方法只是简单将占位符 {} 按照顺序替换为参数<br>
     * 如果想输出 {} 使用 \\转义 { 即可，如果想输出 {} 之前的 \ 使用双转义符 \\\\ 即可<br>
     * 例：<br>
     * 通常使用：format("this is {} for {}", "a", "b") =》 this is a for b<br>
     * 转义{}： format("this is \\{} for {}", "a", "b") =》 this is \{} for a<br>
     * 转义\： format("this is \\\\{} for {}", "a", "b") =》 this is \a for b<br>
     *
     * @param pattern 字符串模板
     * @param args 参数列表
     * @return 结果
     */
    public static String format(String pattern, Object... args){
        if (isBlank(pattern) || ObjectUtils.isEmpty(args)){
            return pattern;
        }

        int patternLength = pattern.length();
        // 初始化定义好的长度以获得更好的性能
        StringBuilder builder = new StringBuilder(patternLength + 50);

        // 记录已经处理到的位置
        int handledPosition = 0;
        // 占位符所在的位置
        int delimIndex;

        for (int argIndex = 0; argIndex < args.length; argIndex++){
            delimIndex = pattern.indexOf(EMPTY_JSON,handledPosition);
            if (delimIndex == -1){
                if (handledPosition == 0){
                    return pattern;
                }
                builder.append(pattern,handledPosition,patternLength);
                return builder.toString();
            }

            // 转义符
            if (delimIndex > 0 && pattern.charAt(delimIndex - 1) == BACKSLASH) {
                // 双转义符
                if (delimIndex > 1 && pattern.charAt(delimIndex - 2) == BACKSLASH) {
                    // 转义符之前还有一个转义符，占位符依旧有效
                    builder.append(pattern, handledPosition, delimIndex - 1);
                    builder.append(StringUtils.toString(args[argIndex]));
                    handledPosition = delimIndex + 2;
                } else {
                    // 占位符被转义
                    argIndex--;
                    builder.append(pattern, handledPosition, delimIndex - 1);
                    builder.append(StringUtils.DELIM_START);
                    handledPosition = delimIndex + 1;
                }
            } else {// 正常占位符
                builder.append(pattern, handledPosition, delimIndex);
                builder.append(StringUtils.toString(args[argIndex]));
                handledPosition = delimIndex + 2;
            }
        }

        // append the characters following the last {} pair.
        // 加入最后一个占位符后所有的字符
        builder.append(pattern, handledPosition, pattern.length());

        return builder.toString();
        
    }



    // toString
    //-----------------------------------------------------------------------

    /**
     * 将对象转为字符串<br>
     * 1、Byte数组和ByteBuffer会被转换为对应字符串的数组 2、对象数组会调用Arrays.toString方法
     *
     * @param obj 对象
     * @return 字符串
     */
    public static String toString(Object obj) {
        return null == obj ? NULL : toString(obj, CharsetUtils.CHARSET_UTF_8);
    }

    /**
     * 将对象转为字符串
     *
     * <pre>
     * 1、Byte数组和ByteBuffer会被转换为对应字符串的数组
     * 2、对象数组会调用Arrays.toString方法
     * </pre>
     *
     * @param obj         对象
     * @param charsetName 字符集
     * @return 字符串
     */
    public static String toString(Object obj, String charsetName) {
        return toString(obj, Charset.forName(charsetName));
    }

    /**
     * 将对象转为字符串
     * <pre>
     * 	 1、Byte数组和ByteBuffer会被转换为对应字符串的数组
     * 	 2、对象数组会调用Arrays.toString方法
     * </pre>
     *
     * @param obj     对象
     * @param charset 字符集
     * @return 字符串
     */
    public static String toString(Object obj, Charset charset) {
        if (null == obj) {
            return null;
        }

        if (obj instanceof String) {
            return (String) obj;
        } else if (obj instanceof byte[]) {
            return toString((byte[]) obj, charset);
        } else if (obj instanceof Byte[]) {
            return toString((Byte[]) obj, charset);
        } else if (obj instanceof ByteBuffer) {
            return toString((ByteBuffer) obj, charset);
        } else if (ObjectUtils.isArray(obj)) {
            return ArrayUtils.toString(obj);
        }

        return obj.toString();
    }

    /**
     * 将byte数组转为字符串
     *
     * @param bytes   byte数组
     * @param charset 字符集
     * @return 字符串
     */
    public static String toString(byte[] bytes, String charset) {
        return toString(bytes, isBlank(charset) ? Charset.defaultCharset() : Charset.forName(charset));
    }

    /**
     * 解码字节码
     *
     * @param data    字符串
     * @param charset 字符集，如果此字段为空，则解码的结果取决于平台
     * @return 解码后的字符串
     */
    public static String toString(byte[] data, Charset charset) {
        if (data == null) {
            return null;
        }

        if (null == charset) {
            return new String(data);
        }
        return new String(data, charset);
    }

    /**
     * 将Byte数组转为字符串
     *
     * @param bytes   byte数组
     * @param charset 字符集
     * @return 字符串
     */
    public static String toString(Byte[] bytes, String charset) {
        return toString(bytes, isBlank(charset) ? Charset.defaultCharset() : Charset.forName(charset));
    }

    /**
     * 解码字节码
     *
     * @param data    字符串
     * @param charset 字符集，如果此字段为空，则解码的结果取决于平台
     * @return 解码后的字符串
     */
    public static String toString(Byte[] data, Charset charset) {
        if (data == null) {
            return null;
        }

        byte[] bytes = new byte[data.length];
        Byte dataByte;
        for (int i = 0; i < data.length; i++) {
            dataByte = data[i];
            bytes[i] = (null == dataByte) ? -1 : dataByte;
        }

        return toString(bytes, charset);
    }

    /**
     * 将编码的byteBuffer数据转换为字符串
     *
     * @param data    数据
     * @param charset 字符集，如果为空使用当前系统字符集
     * @return 字符串
     */
    public static String toString(ByteBuffer data, String charset) {
        if (data == null) {
            return null;
        }

        return toString(data, Charset.forName(charset));
    }

    /**
     * 将编码的byteBuffer数据转换为字符串
     *
     * @param data    数据
     * @param charset 字符集，如果为空使用当前系统字符集
     * @return 字符串
     */
    public static String toString(ByteBuffer data, Charset charset) {
        if (null == charset) {
            charset = Charset.defaultCharset();
        }
        return charset.decode(data).toString();
    }

    /**
     * {@link CharSequence} 转为字符串，null安全
     *
     * @param cs {@link CharSequence}
     * @return 字符串
     */
    public static String toString(CharSequence cs) {
        return null == cs ? null : cs.toString();
    }


    /**
     * {@link InputStream} 转为字符串，null安全
     *
     * @param input    输入流
     * @param encoding 编码格式
     * @return 字符串
     */
    public static String toString(InputStream input, Charset encoding){
        return IOUtils.copy(input,encoding);
    }

    /**
     * {@link InputStream} 转为字符串
     *
     * @param input    输入流
     * @param encoding 编码格式
     * @return 字符串
     */
    public static String toString(InputStream input,String encoding) {
        return toString(input, Charset.forName(encoding));
    }

    /**
     * {@link InputStream} 转为字符串
     *
     * @param input Reader
     * @return 字符串
     * @throws IOException  if an I/O error occurs
     */
    public static String toString(Reader input) throws IOException {
        return IOUtils.copy(input);
    }

    /**
     * Gets the contents at the given URI.
     *
     * @param uri The URI source.
     * @param encoding The encoding name for the URL contents.
     * @return The contents of the URL as a String.
     * @throws IOException if an I/O exception occurs.
     * @since 2.3.
     */
    public static String toString(URI uri, Charset encoding) throws IOException {
        return toString(uri.toURL(), encoding);
    }

    /**
     * Gets the contents at the given URI.
     *
     * @param uri The URI source.
     * @param encoding The encoding name for the URL contents.
     * @return The contents of the URL as a String.
     * @throws IOException                                  if an I/O exception occurs.
     * @throws java.nio.charset.UnsupportedCharsetException thrown instead of {@link java.io
     *                                                      .UnsupportedEncodingException} in version 2.2 if the
     *                                                      encoding is not supported.
     * @since 2.1
     */
    public static String toString(URI uri,String encoding) throws IOException {
        return toString(uri, Charset.forName(encoding));
    }

    /**
     * Gets the contents at the given URL.
     *
     * @param url The URL source.
     * @param encoding The encoding name for the URL contents.
     * @return The contents of the URL as a String.
     * @throws IOException if an I/O exception occurs.
     * @since 2.3
     */
    public static String toString(URL url, Charset encoding) throws IOException {
        try (InputStream inputStream = url.openStream()) {
            return toString(inputStream, encoding);
        }
    }

    /**
     * Gets the contents at the given URL.
     *
     * @param url The URL source.
     * @param encoding The encoding name for the URL contents.
     * @return The contents of the URL as a String.
     * @throws IOException                                  if an I/O exception occurs.
     * @throws java.nio.charset.UnsupportedCharsetException thrown instead of {@link java.io
     *                                                      .UnsupportedEncodingException} in version 2.2 if the
     *                                                      encoding is not supported.
     * @since 2.1
     */
    public static String toString(URL url,String encoding) throws IOException {
        return toString(url, Charset.forName(encoding));
    }

}