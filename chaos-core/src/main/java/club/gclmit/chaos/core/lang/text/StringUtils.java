package club.gclmit.chaos.core.lang.text;

import club.gclmit.chaos.core.exception.ChaosCoreException;
import club.gclmit.chaos.core.lang.Assert;
import club.gclmit.chaos.core.collection.ArrayUtils;
import club.gclmit.chaos.core.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;

import javax.annotation.Nullable;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * <p>
 * String 工具类
 * </p>
 *
 * @author gclm
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    public static final char SLASH = '/';

    public static final char BACKSLASH = '\\';

    public static final char DENIM_START = '{';

    public static final char DENIM_END = '}';

    private static final char EXTENSION_SEPARATOR = '.';

    public static final String NULL = "null";

    public static final String SPACE = " ";

    public static final String EMPTY_JSON = "{}";

    private static final String[] EMPTY_STRING_ARRAY = {};

    private static final String FOLDER_SEPARATOR = "/";

    private static final String WINDOWS_FOLDER_SEPARATOR = "\\";

    private static final String TOP_PATH = "..";

    private static final String CURRENT_PATH = ".";


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
            '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b',
            'c', 'd', 'e', 'f', 'g', 'h',
            'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F',
            'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z'
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
        return new String(buf, Charsets.CHARSET_UTF_8);
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
     * @param args    参数列表
     * @return 结果
     */
    public static String format(String pattern, Object... args) {
        if (isBlank(pattern) || ObjectUtils.isEmpty(args)) {
            return pattern;
        }

        int patternLength = pattern.length();
        // 初始化定义好的长度以获得更好的性能
        StringBuilder builder = new StringBuilder(patternLength + 50);

        // 记录已经处理到的位置
        int handledPosition = 0;
        // 占位符所在的位置
        int delimIndex;

        for (int argIndex = 0; argIndex < args.length; argIndex++) {
            delimIndex = pattern.indexOf(EMPTY_JSON, handledPosition);
            if (delimIndex == -1) {
                if (handledPosition == 0) {
                    return pattern;
                }
                builder.append(pattern, handledPosition, patternLength);
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
                    builder.append(StringUtils.DENIM_START);
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
        return null == obj ? NULL : toString(obj, Charsets.CHARSET_UTF_8);
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
        Assert.notNull(obj, "object 不能为空");
        if (obj instanceof String) {
            return (String) obj;
        } else if (obj instanceof byte[]) {
            return toString((byte[]) obj, charset);
        } else if (obj instanceof Byte[]) {
            return toString((Byte[]) obj, charset);
        } else if (obj instanceof ByteBuffer) {
            return toString((ByteBuffer) obj, charset);
        } else if (ArrayUtils.isArray(obj)) {
            return ArrayUtils.toString(obj);
        }
        return toObjectString(obj);
    }

    /**
     * 通过反射获取 Object 的 属性
     *
     * @param object Object
     * @return java.lang.String
     * @author gclm
     */
    public static String toObjectString(Object object) {
        Assert.notNull(object, "object 不能为空");
        List<Field> fieldList = new ArrayList<>();
        Class clazz = object.getClass();
        String className = clazz.getSimpleName();

        /**
         *  1. 当父类为null的时候说明到达了最上层的父类(Object类).
         *  2. 得到父类,然后赋给自己
         */
        while (clazz != null) {
            fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }

        /**
         * 获取 参数属性封装
         */
        StringBuilder fieldBuilder = StringUtils.builder();
        for (Field field : fieldList) {
            field.setAccessible(true);
            try {
                Object value = isEmpty(field.get(object)) ? null : field.get(object);
                fieldBuilder.append(field.getName()).append("=").append(value).append(", ");
            } catch (IllegalAccessException e) {
                throw new ChaosCoreException("通过反射拼接ToString异常", e);
            }
            field.setAccessible(false);
        }
        String result = StringUtils.substringBefore(fieldBuilder.toString().trim(), ", ");
        return StringUtils.builder(className).append("{").append(result).append("}").toString();
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
    public static String toString(InputStream input, Charset encoding) {
        return IOUtils.copy(input, encoding);
    }

    /**
     * {@link InputStream} 转为字符串
     *
     * @param input    输入流
     * @param encoding 编码格式
     * @return 字符串
     */
    public static String toString(InputStream input, String encoding) {
        return toString(input, Charset.forName(encoding));
    }

    /**
     * {@link InputStream} 转为字符串
     *
     * @param input Reader
     * @return 字符串
     * @throws IOException if an I/O error occurs
     */
    public static String toString(Reader input) throws IOException {
        return IOUtils.copy(input);
    }

    /**
     * Gets the contents at the given URI.
     *
     * @param uri      The URI source.
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
     * @param uri      The URI source.
     * @param encoding The encoding name for the URL contents.
     * @return The contents of the URL as a String.
     * @throws IOException                                  if an I/O exception occurs.
     * @throws java.nio.charset.UnsupportedCharsetException thrown instead of {@link java.io
     *                                                      .UnsupportedEncodingException} in version 2.2 if the
     *                                                      encoding is not supported.
     * @since 2.1
     */
    public static String toString(URI uri, String encoding) throws IOException {
        return toString(uri, Charset.forName(encoding));
    }

    /**
     * Gets the contents at the given URL.
     *
     * @param url      The URL source.
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
     * @param url      The URL source.
     * @param encoding The encoding name for the URL contents.
     * @return The contents of the URL as a String.
     * @throws IOException                                  if an I/O exception occurs.
     * @throws java.nio.charset.UnsupportedCharsetException thrown instead of {@link java.io
     *                                                      .UnsupportedEncodingException} in version 2.2 if the
     *                                                      encoding is not supported.
     * @since 2.1
     */
    public static String toString(URL url, String encoding) throws IOException {
        return toString(url, Charset.forName(encoding));
    }

    //---------------------------------------------------------------------
    // General convenience methods for working with Strings
    //---------------------------------------------------------------------

    /**
     * Check whether the given object (possibly a {@code String}) is empty.
     * This is effectively a shortcut for {@code !hasLength(String)}.
     * <p>This method accepts any Object as an argument, comparing it to
     * {@code null} and the empty String. As a consequence, this method
     * will never return {@code true} for a non-null non-String object.
     * <p>The Object signature is useful for general attribute handling code
     * that commonly deals with Strings but generally has to iterate over
     * Objects since attributes may e.g. be primitive value objects as well.
     * <p><b>Note: If the object is typed to {@code String} upfront, prefer
     * {@link #hasLength(String)} or {@link #hasText(String)} instead.</b>
     *
     * @param str the candidate object (possibly a {@code String})
     * @see #hasLength(String)
     * @see #hasText(String)
     * @since 3.2.1
     */
    public static boolean isEmpty(@Nullable Object str) {
        return (str == null || "".equals(str));
    }

    /**
     * Check that the given {@code CharSequence} is neither {@code null} nor
     * of length 0.
     * <p>Note: this method returns {@code true} for a {@code CharSequence}
     * that purely consists of whitespace.
     * <p><pre class="code">
     * StringUtils.hasLength(null) = false
     * StringUtils.hasLength("") = false
     * StringUtils.hasLength(" ") = true
     * StringUtils.hasLength("Hello") = true
     * </pre>
     *
     * @param str the {@code CharSequence} to check (may be {@code null})
     * @return {@code true} if the {@code CharSequence} is not {@code null} and has length
     * @see #hasLength(String)
     * @see #hasText(CharSequence)
     */
    public static boolean hasLength(@Nullable CharSequence str) {
        return (str != null && str.length() > 0);
    }

    /**
     * Check that the given {@code String} is neither {@code null} nor of length 0.
     * <p>Note: this method returns {@code true} for a {@code String} that
     * purely consists of whitespace.
     *
     * @param str the {@code String} to check (may be {@code null})
     * @return {@code true} if the {@code String} is not {@code null} and has length
     * @see #hasLength(CharSequence)
     * @see #hasText(String)
     */
    public static boolean hasLength(@Nullable String str) {
        return (str != null && !str.isEmpty());
    }

    /**
     * Check whether the given {@code CharSequence} contains actual <em>text</em>.
     * <p>More specifically, this method returns {@code true} if the
     * {@code CharSequence} is not {@code null}, its length is greater than
     * 0, and it contains at least one non-whitespace character.
     * <p><pre class="code">
     * StringUtils.hasText(null) = false
     * StringUtils.hasText("") = false
     * StringUtils.hasText(" ") = false
     * StringUtils.hasText("12345") = true
     * StringUtils.hasText(" 12345 ") = true
     * </pre>
     *
     * @param str the {@code CharSequence} to check (may be {@code null})
     * @return {@code true} if the {@code CharSequence} is not {@code null},
     * its length is greater than 0, and it does not contain whitespace only
     * @see #hasText(String)
     * @see #hasLength(CharSequence)
     * @see Character#isWhitespace
     */
    public static boolean hasText(@Nullable CharSequence str) {
        return (str != null && str.length() > 0 && containsText(str));
    }

    /**
     * Check whether the given {@code String} contains actual <em>text</em>.
     * <p>More specifically, this method returns {@code true} if the
     * {@code String} is not {@code null}, its length is greater than 0,
     * and it contains at least one non-whitespace character.
     *
     * @param str the {@code String} to check (may be {@code null})
     * @return {@code true} if the {@code String} is not {@code null}, its
     * length is greater than 0, and it does not contain whitespace only
     * @see #hasText(CharSequence)
     * @see #hasLength(String)
     * @see Character#isWhitespace
     */
    public static boolean hasText(@Nullable String str) {
        return (str != null && !str.isEmpty() && containsText(str));
    }

    private static boolean containsText(CharSequence str) {
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check whether the given {@code CharSequence} contains any whitespace characters.
     *
     * @param str the {@code CharSequence} to check (may be {@code null})
     * @return {@code true} if the {@code CharSequence} is not empty and
     * contains at least 1 whitespace character
     * @see Character#isWhitespace
     */
    public static boolean containsWhitespace(@Nullable CharSequence str) {
        if (!hasLength(str)) {
            return false;
        }

        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check whether the given {@code String} contains any whitespace characters.
     *
     * @param str the {@code String} to check (may be {@code null})
     * @return {@code true} if the {@code String} is not empty and
     * contains at least 1 whitespace character
     * @see #containsWhitespace(CharSequence)
     */
    public static boolean containsWhitespace(@Nullable String str) {
        return containsWhitespace((CharSequence) str);
    }

    /**
     * Trim leading and trailing whitespace from the given {@code String}.
     *
     * @param str the {@code String} to check
     * @return the trimmed {@code String}
     * @see java.lang.Character#isWhitespace
     */
    public static String trimWhitespace(String str) {
        if (!hasLength(str)) {
            return str;
        }

        int beginIndex = 0;
        int endIndex = str.length() - 1;

        while (beginIndex <= endIndex && Character.isWhitespace(str.charAt(beginIndex))) {
            beginIndex++;
        }

        while (endIndex > beginIndex && Character.isWhitespace(str.charAt(endIndex))) {
            endIndex--;
        }

        return str.substring(beginIndex, endIndex + 1);
    }

    /**
     * Trim <i>all</i> whitespace from the given {@code String}:
     * leading, trailing, and in between characters.
     *
     * @param str the {@code String} to check
     * @return the trimmed {@code String}
     * @see java.lang.Character#isWhitespace
     */
    public static String trimAllWhitespace(String str) {
        if (!hasLength(str)) {
            return str;
        }

        int len = str.length();
        StringBuilder sb = new StringBuilder(str.length());
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            if (!Character.isWhitespace(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * Trim leading whitespace from the given {@code String}.
     *
     * @param str the {@code String} to check
     * @return the trimmed {@code String}
     * @see java.lang.Character#isWhitespace
     */
    public static String trimLeadingWhitespace(String str) {
        if (!hasLength(str)) {
            return str;
        }

        StringBuilder sb = new StringBuilder(str);
        while (sb.length() > 0 && Character.isWhitespace(sb.charAt(0))) {
            sb.deleteCharAt(0);
        }
        return sb.toString();
    }

    /**
     * Trim trailing whitespace from the given {@code String}.
     *
     * @param str the {@code String} to check
     * @return the trimmed {@code String}
     * @see java.lang.Character#isWhitespace
     */
    public static String trimTrailingWhitespace(String str) {
        if (!hasLength(str)) {
            return str;
        }

        StringBuilder sb = new StringBuilder(str);
        while (sb.length() > 0 && Character.isWhitespace(sb.charAt(sb.length() - 1))) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * Trim all occurrences of the supplied leading character from the given {@code String}.
     *
     * @param str              the {@code String} to check
     * @param leadingCharacter the leading character to be trimmed
     * @return the trimmed {@code String}
     */
    public static String trimLeadingCharacter(String str, char leadingCharacter) {
        if (!hasLength(str)) {
            return str;
        }

        StringBuilder sb = new StringBuilder(str);
        while (sb.length() > 0 && sb.charAt(0) == leadingCharacter) {
            sb.deleteCharAt(0);
        }
        return sb.toString();
    }

    /**
     * Trim all occurrences of the supplied trailing character from the given {@code String}.
     *
     * @param str               the {@code String} to check
     * @param trailingCharacter the trailing character to be trimmed
     * @return the trimmed {@code String}
     */
    public static String trimTrailingCharacter(String str, char trailingCharacter) {
        if (!hasLength(str)) {
            return str;
        }

        StringBuilder sb = new StringBuilder(str);
        while (sb.length() > 0 && sb.charAt(sb.length() - 1) == trailingCharacter) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * Test if the given {@code String} matches the given single character.
     *
     * @param str             the {@code String} to check
     * @param singleCharacter the character to compare to
     * @since 5.2.9
     */
    public static boolean matchesCharacter(@Nullable String str, char singleCharacter) {
        return (str != null && str.length() == 1 && str.charAt(0) == singleCharacter);
    }

    /**
     * Test if the given {@code String} starts with the specified prefix,
     * ignoring upper/lower case.
     *
     * @param str    the {@code String} to check
     * @param prefix the prefix to look for
     * @see java.lang.String#startsWith
     */
    public static boolean startsWithIgnoreCase(@Nullable String str, @Nullable String prefix) {
        return (str != null && prefix != null && str.length() >= prefix.length() &&
                str.regionMatches(true, 0, prefix, 0, prefix.length()));
    }

    /**
     * Test if the given {@code String} ends with the specified suffix,
     * ignoring upper/lower case.
     *
     * @param str    the {@code String} to check
     * @param suffix the suffix to look for
     * @see java.lang.String#endsWith
     */
    public static boolean endsWithIgnoreCase(@Nullable String str, @Nullable String suffix) {
        return (str != null && suffix != null && str.length() >= suffix.length() &&
                str.regionMatches(true, str.length() - suffix.length(), suffix, 0, suffix.length()));
    }

    /**
     * Test whether the given string matches the given substring
     * at the given index.
     *
     * @param str       the original string (or StringBuilder)
     * @param index     the index in the original string to start matching against
     * @param substring the substring to match at the given index
     */
    public static boolean substringMatch(CharSequence str, int index, CharSequence substring) {
        if (index + substring.length() > str.length()) {
            return false;
        }
        for (int i = 0; i < substring.length(); i++) {
            if (str.charAt(index + i) != substring.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Count the occurrences of the substring {@code sub} in string {@code str}.
     *
     * @param str string to search in
     * @param sub string to search for
     */
    public static int countOccurrencesOf(String str, String sub) {
        if (!hasLength(str) || !hasLength(sub)) {
            return 0;
        }

        int count = 0;
        int pos = 0;
        int idx;
        while ((idx = str.indexOf(sub, pos)) != -1) {
            ++count;
            pos = idx + sub.length();
        }
        return count;
    }

    /**
     * Replace all occurrences of a substring within a string with another string.
     *
     * @param inString   {@code String} to examine
     * @param oldPattern {@code String} to replace
     * @param newPattern {@code String} to insert
     * @return a {@code String} with the replacements
     */
    public static String replace(String inString, String oldPattern, @Nullable String newPattern) {
        if (!hasLength(inString) || !hasLength(oldPattern) || newPattern == null) {
            return inString;
        }
        int index = inString.indexOf(oldPattern);
        if (index == -1) {
            // no occurrence -> can return input as-is
            return inString;
        }

        int capacity = inString.length();
        if (newPattern.length() > oldPattern.length()) {
            capacity += 16;
        }
        StringBuilder sb = new StringBuilder(capacity);

        int pos = 0;  // our position in the old string
        int patLen = oldPattern.length();
        while (index >= 0) {
            sb.append(inString, pos, index);
            sb.append(newPattern);
            pos = index + patLen;
            index = inString.indexOf(oldPattern, pos);
        }

        // append any characters to the right of a match
        sb.append(inString, pos, inString.length());
        return sb.toString();
    }

    /**
     * Delete all occurrences of the given substring.
     *
     * @param inString the original {@code String}
     * @param pattern  the pattern to delete all occurrences of
     * @return the resulting {@code String}
     */
    public static String delete(String inString, String pattern) {
        return replace(inString, pattern, "");
    }

    /**
     * Delete any character in a given {@code String}.
     *
     * @param inString      the original {@code String}
     * @param charsToDelete a set of characters to delete.
     *                      E.g. "az\n" will delete 'a's, 'z's and new lines.
     * @return the resulting {@code String}
     */
    public static String deleteAny(String inString, @Nullable String charsToDelete) {
        if (!hasLength(inString) || !hasLength(charsToDelete)) {
            return inString;
        }

        int lastCharIndex = 0;
        char[] result = new char[inString.length()];
        for (int i = 0; i < inString.length(); i++) {
            char c = inString.charAt(i);
            if (charsToDelete.indexOf(c) == -1) {
                result[lastCharIndex++] = c;
            }
        }
        if (lastCharIndex == inString.length()) {
            return inString;
        }
        return new String(result, 0, lastCharIndex);
    }
}