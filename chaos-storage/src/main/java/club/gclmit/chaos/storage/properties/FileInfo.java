package club.gclmit.chaos.storage.properties;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 * 文件详情
 * </p>
 *
 * @author: gclm
 * @date: 2020/3/16 5:35 下午
 * @version: V1.0
 * @since 1.8
 */
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
    private Integer ossType = StorageServer.ALIYUN.getId();

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


    public FileInfo() {
    }

    public FileInfo(Integer status) {
        this.status = status;
    }

    public FileInfo(String name, String contentType, Long size, String md5, String ossKey, Integer ossType) {
        this.name = name;
        this.contentType = contentType;
        this.size = size;
        this.md5 = md5;
        this.ossKey = ossKey;
        this.ossType = ossType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String geteTag() {
        return eTag;
    }

    public void seteTag(String eTag) {
        this.eTag = eTag;
    }

    public long getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(long uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOssKey() {
        return ossKey;
    }

    public void setOssKey(String ossKey) {
        this.ossKey = ossKey;
    }

    public Integer getOssType() {
        return ossType;
    }

    public void setOssType(Integer ossType) {
        this.ossType = ossType;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", contentType='" + contentType + '\'' +
                ", url='" + url + '\'' +
                ", size=" + size +
                ", md5='" + md5 + '\'' +
                ", eTag='" + eTag + '\'' +
                ", ossKey='" + ossKey + '\'' +
                ", ossType=" + ossType +
                ", uploadTime=" + uploadTime +
                ", status=" + status +
                '}';
    }
}
