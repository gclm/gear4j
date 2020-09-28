package club.gclmit.chaos.core.util;


import club.gclmit.chaos.core.text.StringUtils;
import org.springframework.lang.Nullable;

/**
 * 系统工具类
 *
 * @author gclm
 */
public class SystemUtils {

    /**
     * Linux
     */
    private static final String OS_LINUX = "LINUX";

    /**
     * Mac
     */
    private static final String OS_MAC = "Mac";

    /**
     * Windows
     */
    private static final String OS_WINDOWS = "Windows";

    /**
     * 获取 user home
     */
    @Nullable
    public static final String USER_HOME = getSystemProperty("user.home");

    /**
     * 获取用户地址
     */
    @Nullable
    public static final String USER_DIR = getSystemProperty("user.dir");

    /**
     * 获取用户名
     */
    @Nullable
    public static final String USER_NAME = getSystemProperty("user.name");

    /**
     * os 名
     */
    @Nullable
    public static final String OS_NAME = getSystemProperty("os.name");

    /**
     * <p>
     * Gets a System property, defaulting to {@code null} if the property cannot be read.
     * </p>
     * <p>
     * If a {@code SecurityException} is caught, the return value is {@code null} and a message is written to
     * {@code System.err}.
     * </p>
     *
     * @param property the system property name
     * @return the system property value or {@code null} if a security problem occurs
     */
    @Nullable
    private static String getSystemProperty(final String property) {
        try {
            return System.getProperty(property);
        } catch (final SecurityException ex) {
            return null;
        }
    }

    /**
     * 判断是否为Linux
     *
     * @return boolean
     */
    public static boolean isLinux() {
        return StringUtils.isNotBlank(OS_NAME) && OS_LINUX.equals(OS_NAME.toUpperCase());
    }

    /**
     * 判断是否为 Windows
     *
     * @return boolean
     */
    public static boolean isWindows() {
        return StringUtils.isNotBlank(OS_NAME) && OS_WINDOWS.equals(OS_NAME.toUpperCase());
    }

    /**
     * 判断是否为 mac
     *
     * @return boolean
     */
    public static boolean isMac() {
        return StringUtils.isNotBlank(OS_NAME) && OS_MAC.equals(OS_NAME.toUpperCase());
    }
}
