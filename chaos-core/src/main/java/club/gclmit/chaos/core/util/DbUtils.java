package club.gclmit.chaos.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * <p>
 *  数据相关服务
 * </p>
 *
 * @author: gclm
 * @date: 2020/3/24 10:51 上午
 * @version: V1.0
 * @since 1.8
 */
public class DbUtils {

    private static final Logger log = LoggerFactory.getLogger(DbUtils.class);

    /**
     *  判断数据是否修改成功
     *
     * @author gclm
     * @param: result
     * @date 2020/3/24 10:51 上午
     * @return: boolean
     * @throws
     */
    public static boolean retBool(Integer result) {
        return null != result && result >= 1;
    }


    /**
     * @param host     数据库服务器主机地址，可以是ip，也可以是域名
     * @param port     数据库服务器端口
     * @param dbName   数据库名字
     * @param username 数据库用户名
     * @param password 数据库密码（明文）
     * @param filePath 存到哪个文件，形如："d:/dbbackup/2019-08-03_00_00_00.sql"
     * @return
     */
    public static File backup(String host, int port, String dbName, String username, String password, String filePath) {
        Long startTime = DateUtils.getMilliTimestamp();

        try {
            File file = new File(filePath);
            String[] commands = new String[3];

            if (SystemUtils.IS_OS_WINDOWS){
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
                log.info("数据库【{}】备份成功，耗时：{} ms",dbName,distance);
                return file;
            } else {
                InputStream is = process.getErrorStream();
                if (is != null) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(is, CharsetUtils.UTF_8));
                    String line;
                    StringBuilder sb = new StringBuilder();
                    while ((line = in.readLine()) != null) {
                        sb.append(line);
                    }
                    log.info("数据库【{}】备份失败,错误信息:{}",dbName,sb.toString());
                }
            }
        } catch (Exception e) {
            log.error("数据库【{}】备份失败，异常:{}",dbName,e);
            return null;
        }
        return null;
    }

}
