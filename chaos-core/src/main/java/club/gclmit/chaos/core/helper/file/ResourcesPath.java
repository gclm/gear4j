package club.gclmit.chaos.core.helper.file;

import java.io.*;

import java.util.Objects;

/**
 * <p>
 * Maven Resources Path 路径
 * </p>
 *
 * @author: gclm
 * @date: 2019/11/30 19:34
 * @version: V1.0
 * @since 1.8
 */
public class ResourcesPath {


    public InputStream read(String path) {
        return  this.getClass().getClassLoader().getResourceAsStream(path);
    }

    public BufferedReader bufferRead(String fileName) throws IOException {
        String path = new StringBuilder().append(System.getProperty("user.dir"))
                .append(File.separator).append("src")
                .append(File.separator).append("main")
                .append(File.separator).append("resources")
                .append(File.separator).append(fileName).toString();
        System.out.println(path);
       try(BufferedReader reader = new BufferedReader(new FileReader(path))){
           return reader;
       }
    }

    /**
     * <p>
     *  根据文件名获取文件路径
     * </p>
     *
     * @author gclm
     * @param: fileName
     * @date 2019/11/30 19:40
     * @return: java.lang.String
     * @throws
     */
    public String getPath(String fileName) {
        return Objects.requireNonNull(this.getClass().getClassLoader().getResource(fileName)).getPath();
    }

    /**
     * <p>
     *  获取 rescoures 根路径
     * </p>
     *
     * @author gclm
     * @date 2019/11/30 19:40
     * @return: java.lang.String
     * @throws
     */
    public String getPath(){
       return getPath("");
    }
}
