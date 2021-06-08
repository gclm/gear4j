package club.gclmit.chaos.core.utils;

import club.gclmit.chaos.core.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Maven清理 *.lastupload 文件
 *
 * @author gclm
 * @date 2021/5/10 下午2:33
 * @since 1.0
 */
public class FileUtilsTest {

    @Test
    public void maven_clean() {
        List<File> files = FileUtils.loopFiles("/home/gclm/.m2/repository");
        List<File> deleteFiles = files.stream().filter(file -> FileUtils.getName(file).endsWith(".lastUpdated")).collect(Collectors.toList());
        deleteFiles.forEach(file -> {
            System.out.println("删除文件：" + file.getAbsolutePath());
            FileUtils.del(file);
        });
    }
}
