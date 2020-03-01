package club.gclmit.chaos.core.helper.file;

import java.util.Objects;

/**
 * <p>
 * 路径工具类
 * </p>
 *
 * @author: gclm
 * @date: 2020/2/29 1:22 下午
 * @version: V1.0
 * @since 1.8
 */
public class PathHelper {

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
    public  String getPath(String fileName) {
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
    public  String getPath(){
        return getPath("");
    }
}
