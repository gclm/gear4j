package club.gclmit.chaos.core.lang.log;

import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

/**
 * 控制台信息打印
 *
 * @author gclm
 * @since 1.8
 */
public class Console {

    /**
     * 控制台信息打印
     *
     * @author gclm
     * @param template       消息模板
     * @param arguments     消息参数
     */
    public static void print(String template, Object... arguments){
        FormattingTuple ft = MessageFormatter.arrayFormat(template, arguments);
        System.out.println(ft.getMessage());
    }
}
