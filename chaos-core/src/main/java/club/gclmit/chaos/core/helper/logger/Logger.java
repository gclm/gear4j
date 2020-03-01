package club.gclmit.chaos.core.helper.logger;


import org.slf4j.LoggerFactory;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

/**
 * <p>
 * Slf4j 日志封装
 * </p>
 *
 * @author: gclm
 * @date: 2020/1/4 9:17 下午
 * @version: V1.0
 * @since 1.8
 */
public class Logger {

    // 当前日志类名
    private final static String logClassName = Logger.class.getName();

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
            if (element.getClassName().equals(logClassName)) {
                isEachLogFlag = true;
            }

            // 下一个非日志类的堆栈，就是最原始被调用的方法
            if (isEachLogFlag) {
                if (!element.getClassName().equals(logClassName)) {
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

    public static void info(LoggerServer loggerServer, String format, Object... arguments) {
        log().info(buildMessage(loggerServer, format, arguments));
    }

    public static void debug(LoggerServer loggerServer, String format, Object... arguments) {
        log().debug(buildMessage(loggerServer, format, arguments));
    }

    public static void warn(LoggerServer loggerServer, String format, Object... arguments) {
        log().warn(buildMessage(loggerServer, format, arguments));
    }

    public static void error(LoggerServer loggerServer, String format, Object... arguments) {
        log().error(buildMessage(loggerServer, format, arguments));
    }

    /**
     *  消息组建
     *
     * @author gclm
     * @param: logServer
     * @param: message
     * @date 2020/1/4 9:27 下午
     * @return: java.lang.String
     * @throws
     */
    private static String buildMessage(LoggerServer loggerServer,String format, Object... arguments){
        FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
        return String.format("[%s]:%s", loggerServer.getKey(), ft.getMessage());
    }
}
