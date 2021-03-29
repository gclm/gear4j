package club.gclmit.chaos.storage.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import club.gclmit.chaos.storage.contants.StorageServer;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

/**
 * <p>
 * 文件详情
 * </p>
 *
 * @author gclm
 */
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@TableName("chaos_file_info")
@ApiModel(value = "文件服务对象")
public class FileInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键 编号
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 文件名
     */
    @ApiModelProperty(value = "文件名")
    private String name;

    /**
     * 文件类型
     */
    @ApiModelProperty(value = "文件类型")
    private String contentType;

    /**
     * 文件路径
     */
    @ApiModelProperty(value = "文件路径")
    private String url;

    /**
     * 文件大小
     */
    @ApiModelProperty(value = "文件大小")
    private Long size;

    /**
     * md5
     */
    @ApiModelProperty(value = "md5")
    private String md5;

    /**
     * ETag
     */
    @ApiModelProperty(value = "ETag")
    private String eTag;

    /**
     * OSS key
     */
    @ApiModelProperty(value = "OSS key")
    private String ossKey;

    /**
     * OSS type
     */
    @ApiModelProperty(value = "OSS类型")
    private String ossType = StorageServer.ALIYUN.getCode();

    /**
     * 上传时间
     */
    @ApiModelProperty(value = "上传时间")
    private long uploadTime;

    /**
     * 文件状态
     */
    @ApiModelProperty(value = "文件状态")
    private Integer status;

    public FileInfo(Integer status) {
        this.status = status;
    }

    public FileInfo(String name, String contentType, Long size, String md5, String ossKey, String ossType) {
        this.name = name;
        this.contentType = contentType;
        this.size = size;
        this.md5 = md5;
        this.ossKey = ossKey;
        this.ossType = ossType;
    }
}
