package club.gclmit.chaos.core.lang.log;

import org.slf4j.LoggerFactory;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

/**
 * <p>
 * Slf4j 日志封装
 * </p>
 *
 * @author gclm
 */
public class Logger {

    /**
     *  当前日志类名
     */
    private final static String CURRENT_LOG_CLASS_NAME = Logger.class.getName();

    /**
     * 获取最原始被调用的堆栈信息
     */
    private static StackTraceElement getCaller() {

        // 获取堆栈信息
        StackTraceElement[] traceElements = Thread.currentThread()
                .getStackTrace();
        if (null == traceElements) {
            return null;
        }

        // 最原始被调用的堆栈信息
        StackTraceElement caller = null;

        // 循环遍历到日志类标识
        boolean isEachLogFlag = false;

        // 遍历堆栈信息，获取出最原始被调用的方法信息
        // 当前日志类的堆栈信息完了就是调用该日志类对象信息
        for (StackTraceElement element : traceElements) {
            // 遍历到日志类
            if (element.getClassName().equals(CURRENT_LOG_CLASS_NAME)) {
                isEachLogFlag = true;
            }

            // 下一个非日志类的堆栈，就是最原始被调用的方法
            if (isEachLogFlag) {
                if (!element.getClassName().equals(CURRENT_LOG_CLASS_NAME)) {
                    caller = element;
                    break;
                }
            }
        }

        return caller;
    }

    /**
     * 自动匹配请求类名，生成logger对象
     */
    private static org.slf4j.Logger log() {
        // 最原始被调用的堆栈对象
        StackTraceElement caller = getCaller();
        // 空堆栈处理
        if (caller == null) {
            return LoggerFactory.getLogger(Logger.class);
        }

        // 取出被调用对象的类名，并构造一个Logger对象返回
        return LoggerFactory.getLogger(caller.getClassName());
    }

    /**
     *  trace
     *
     * @author gclm
     * @param format        消息模板
     * @param arguments     消息参数
     */
    public static void trace(String format, Object... arguments) {
        log().trace(build(null, format, arguments));
    }

    /**
     *  info
     *
     * @author gclm
     * @param format        消息模板
     * @param arguments     消息参数
     */
    public static void info(String format, Object... arguments) {
        log().info(build(null, format, arguments));
    }

    /**
     *  debug
     *
     * @author gclm
     * @param format        消息模板
     * @param arguments     消息参数
     */
    public static void debug(String format, Object... arguments) {
        log().debug(build(null, format, arguments));
    }

    /**
     *  warn
     *
     * @author gclm
     * @param format        消息模板
     * @param arguments     消息参数
     */
    public static void warn(String format, Object... arguments) {
        log().warn(build(null, format, arguments));
    }

    /**
     *  error
     *
     * @author gclm
     * @param format        消息模板
     * @param arguments     消息参数
     */
    public static void error(String format, Object... arguments) {
        log().error(build(null, format, arguments));
    }

    /**
     *  trace
     *
     * @author gclm
     * @param loggerServer  消息服务类型
     * @param format        消息模板
     * @param arguments     消息参数
     */
    public static void trace(LoggerServer loggerServer, String format, Object... arguments) {
        log().trace(build(loggerServer, format, arguments));
    }

    /**
     *  info
     *
     * @author gclm
     * @param loggerServer  消息服务类型
     * @param format        消息模板
     * @param arguments     消息参数
     */
    public static void info(LoggerServer loggerServer, String format, Object... arguments) {
        log().info(build(loggerServer, format, arguments));
    }

    /**
     *  debug
     *
     * @author gclm
     * @param loggerServer  消息服务类型
     * @param format        消息模板
     * @param arguments     消息参数
     */
    public static void debug(LoggerServer loggerServer, String format, Object... arguments) {
        log().debug(build(loggerServer, format, arguments));
    }

    /**
     *  warn
     *
     * @author gclm
     * @param loggerServer  消息服务类型
     * @param format        消息模板
     * @param arguments     消息参数
     */
    public static void warn(LoggerServer loggerServer, String format, Object... arguments) {
        log().warn(build(loggerServer, format, arguments));
    }

    /**
     *  error
     *
     * @author gclm
     * @param loggerServer  消息服务类型
     * @param format        消息模板
     * @param arguments     消息参数
     */
    public static void error(LoggerServer loggerServer, String format, Object... arguments) {
        log().error(build(loggerServer, format, arguments));
    }

    /**
     *  消息组建
     *
     * @author gclm
     * @param loggerServer  消息服务类型
     * @param template      消息模板
     * @param arguments     消息参数
     * @return java.lang.String
     */
    private static String build(LoggerServer loggerServer, String template, Object... arguments){
        FormattingTuple ft = MessageFormatter.arrayFormat(template, arguments);
        if (loggerServer != null) {
            return String.format("[%s]:%s", loggerServer.getKey(), ft.getMessage());
        }
        return ft.getMessage();
    }
}
