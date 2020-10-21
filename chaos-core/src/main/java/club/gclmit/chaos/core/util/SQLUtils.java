package club.gclmit.chaos.core.util;

import club.gclmit.chaos.core.io.IOUtils;
import club.gclmit.chaos.core.lang.text.RulesUtils;
import club.gclmit.chaos.core.lang.text.StringUtils;
import club.gclmit.chaos.core.lang.text.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * <p>
 * sql 工具类
 * </p>
 * @author gclm
 */
public class SQLUtils {

    private static final Logger log = LoggerFactory.getLogger(SQLUtils.class);

    /**
     * 仅支持字母、数字、下划线、空格、逗号（支持多个字段排序）
     */
    public static String SQL_PATTERN = "[a-zA-Z0-9_\\ \\,]+";

    private SQLUtils() {
    }

    /**
     * 检查字符，防止注入绕过
     * @param value 字符
     * @return String
     */
    public static String escapeOrderBySql(String value) {
        if (StringUtils.isNotEmpty(value) && !RulesUtils.isMatch(SQL_PATTERN, value)) {
            return StringUtils.EMPTY;
        }
        return value;
    }

    /**
     *  判断数据是否修改成功
     *
     * @author gclm
     * @param result 处理结果次数
     * @return: boolean
     */
    public static boolean retBool(Integer result) {
        return null != result && result >= 1;
    }

    /**
     * 备份 SQL
     * @param host     数据库服务器主机地址，可以是ip，也可以是域名
     * @param port     数据库服务器端口
     * @param dbName   数据库名字
     * @param username 数据库用户名
     * @param password 数据库密码（明文）
     * @param filePath 存到哪个文件，形如："d:/dbbackup/2019-08-03_00_00_00.sql"
     * @return File
     */
    public static File backup(String host, int port, String dbName, String username, String password, String filePath) {
        Long startTime = DateUtils.getMilliTimestamp();

        try {
            File file = new File(filePath);
            String[] commands = new String[3];

            if (SystemUtils.isWindows()){
                commands[0] = "cmd.exe";
                commands[1] = "/c";
            } else {
                commands[0] = "/bin/sh";
                commands[1] = "-c";
            }

            StringBuilder mysqldump = new StringBuilder();
            mysqldump.append("mysqldump");
            mysqldump.append(" --opt");
            mysqldump.append(" --user=").append(username);
            mysqldump.append(" --password=").append(password);
            mysqldump.append(" --host=").append(host);
            mysqldump.append(" --protocol=tcp");
            mysqldump.append(" --port=").append(port);
            mysqldump.append(" --default-character-set=utf8");
            mysqldump.append(" --single-transaction=TRUE");
            mysqldump.append(" --routines");
            mysqldump.append(" --events");
            mysqldump.append(" ").append(dbName);
            mysqldump.append(" > ");

            String command = mysqldump.toString();
            commands[2] = command;
            log.debug("备份sql:{}",command);

            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(commands);

            if (process.waitFor() == 0) {
                Long endTime = DateUtils.getMilliTimestamp();
                Long distance = endTime - startTime;
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                IOUtils.copy(process.getInputStream(),fileOutputStream);
                IOUtils.closeQuietly(fileOutputStream);
                log.info("数据库【{}】备份成功，耗时：{} ms",dbName,distance);
                return file;
            } else {
                InputStream is = process.getErrorStream();
                String error = IOUtils.copyToString(is,Charsets.CHARSET_UTF_8);
                log.info("数据库【{}】备份失败,错误信息:{}",dbName,error);
            }
        } catch (Exception e) {
            log.error("数据库【{}】备份失败，异常:{}",dbName,e);
            return null;
        }
        return null;
    }
}
