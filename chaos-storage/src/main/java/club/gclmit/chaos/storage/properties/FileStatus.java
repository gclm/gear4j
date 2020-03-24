package club.gclmit.chaos.storage.properties;

/**
 * <p>
 *  文件状态枚举
 * </p>
 *
 * @author: gclm
 * @date: 2020/3/16 5:59 下午
 * @version: V1.0
 * @since 1.8
 */
public enum  FileStatus {

    /**
     * 上传文件成功
     */
    UPLOAD_SUCCESS(1,"上传成功"),

    /**
     * 上传文件失败
     */
    UPLOAD_FAIL(-1,"上传失败"),

    /**
     * OSS 存在该文件
     */
    OSS_FILE_OK(2,"OSS文件存在"),

    /**
     * OSS 删除文件
     */
    OSS_FILE_FAIL(-2,"OSS文件已删除");

    /**
     * id
     */
    private Integer id;

    /**
     * 消息
     */
    private String message;

    public Integer getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    FileStatus(Integer id, String message) {
        this.id = id;
        this.message = message;
    }
}
