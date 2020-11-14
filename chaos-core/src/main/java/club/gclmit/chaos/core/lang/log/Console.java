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
     * @param template 消息模板
     * @param args     消息参数
     */
    public static void log(String template, Object... args){
        FormattingTuple ft = MessageFormatter.arrayFormat(template, args);
        System.out.println(ft.getMessage());
    }
}
