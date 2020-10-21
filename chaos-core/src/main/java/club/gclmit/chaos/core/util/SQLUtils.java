package club.gclmit.chaos.core.util;

import club.gclmit.chaos.core.exception.ChaosCoreException;
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
 *
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
     *
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
     * 判断数据是否修改成功
     *
     * @param result 处理结果次数
     * @author gclm
     * @return: boolean
     */
    public static boolean retBool(Integer result) {
        return null != result && result >= 1;
    }

    /**
     * 备份 SQL
     *
     * @param host     数据库服务器主机地址，可以是ip，也可以是域名
     * @param port     数据库服务器端口
     * @param dbName   数据库名字
     * @param username 数据库用户名
     * @param password 数据库密码（明文）
     * @param filePath 存到哪个文件，形如："d:/dbbackup/2019-08-03_00_00_00.sql"
     * @return File
     */
    public static File backup(String host, int port, String dbName, String username, String password, String filePath) {
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
        InputStream stream = ShellUtils.exec(command);
        File file = new File(filePath);
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            IOUtils.copy(stream, fileOutputStream);
            return file;
        } catch (IOException e) {
            throw new ChaosCoreException("备份SQL失败", e);
        }
    }
}
