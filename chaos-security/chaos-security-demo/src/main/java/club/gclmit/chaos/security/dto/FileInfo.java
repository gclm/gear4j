package club.gclmit.chaos.security.dto;

/**
 * <p>
 * 文件信息服务
 * </p>
 *
 * @author: gclm
 * @date: 2019/12/4 10:42 上午
 * @version: V1.0
 * @since 1.8
 */
public class FileInfo {

    private String path;

    public FileInfo(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
