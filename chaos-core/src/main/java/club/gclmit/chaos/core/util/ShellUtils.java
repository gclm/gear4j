package club.gclmit.chaos.core.util;

import club.gclmit.chaos.core.exception.ChaosCoreException;
import club.gclmit.chaos.core.io.IOUtils;
import club.gclmit.chaos.core.lang.text.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 系统运行时工具类，用于执行系统命令的工具
 *
 * @author gclm
 * @since 2020/10/21 17:16
 * @since 1.8
 */
public class ShellUtils {

    private static Logger logger = LoggerFactory.getLogger(ShellUtils.class);

    /**
     * 执行系统命令，使用系统默认编码
     *
     * @param cmd 命令列表
     * @return 执行结果
     * @throws ChaosCoreException 自定义异常
     */
    public static String execForString(String cmd){
        InputStream stream = exec(cmd);
        if (IOUtils.isNotEmpty(stream)){
            return IOUtils.copy(stream, Charsets.CHARSET_UTF_8);
        }
        return "";
    }

    /**
     * 执行系统命令，使用系统默认编码
     *
     * @param cmd 命令列表
     * @return 执行结果
     * @throws ChaosCoreException 自定义异常
     */
    public static List<String> execForLines(String cmd){
        InputStream stream = exec(cmd);
        if (IOUtils.isNotEmpty(stream)){
            return IOUtils.readLines(stream, Charsets.CHARSET_UTF_8);
        }
        return new ArrayList<>(0);
    }

    /**
     * 执行系统命令，使用系统默认编码
     *
     * @param cmd 命令列表，每个元素代表一条命令
     * @return 执行结果，按行区分
     * @throws ChaosCoreException 自定义异常
     */
    public static InputStream exec(String cmd){
        Long startTime = DateUtils.getMilliTimestamp();

        String[] commands = new String[3];
        if (SystemUtils.isWindows()){
            commands[0] = "cmd.exe";
            commands[1] = "/c";
        } else {
            commands[0] = "/bin/sh";
            commands[1] = "-c";
        }

        commands[2] = cmd;

        try {
            Process process = Runtime.getRuntime().exec(commands);
            String shell = Arrays.toString(commands);

            if (process.waitFor() == 0) {
                InputStream stream = process.getInputStream();
                Long endTime = DateUtils.getMilliTimestamp();
                Long distance = endTime - startTime;

                logger.debug("命令:[{}]\t耗时:[{}]",shell,distance);
                return stream;
            } else {
                InputStream is = process.getErrorStream();
                if (is != null) {
                    String error = IOUtils.copyToString(is,Charsets.CHARSET_UTF_8);
                    logger.debug("状态:[{}]\t命令:[{}]\n错误:[{}]",false,shell,error);
                }
                return null;
            }
        } catch (Exception e){
            throw new ChaosCoreException("执行 Shell 命令发生异常", e);
        }
    }
}
