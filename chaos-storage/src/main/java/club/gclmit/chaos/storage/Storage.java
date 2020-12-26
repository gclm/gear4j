package club.gclmit.chaos.storage;

import club.gclmit.chaos.storage.contants.ResponseDataType;
import club.gclmit.chaos.storage.contants.StorageServer;
import club.gclmit.chaos.storage.pojo.CloudStorage;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p>
 *  storage 配置
 * </p>
 *
 * @author gclm
 */
@Getter
@Setter
@ToString
public class Storage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 类型
     * 1：阿里云    2：七牛
     * 3：腾讯云    4：又拍云
     * 5：UCLOUD
     */
    private StorageServer type = StorageServer.ALIYUN;

    /**
     *  OSS 厂商配置
     */
    private CloudStorage config = new CloudStorage();

    /**
     * 数据类型
     */
    private ResponseDataType result = ResponseDataType.DETAIL;

    /**
     * 保存数据库
     * true 为保存，false为不保存
     */
    private boolean db = false;
}
