package club.gclmit.chaos.logger.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 *  日志配置工具类
 * </p>
 *
 * @author gclm
 */
@Getter
@Setter
@ToString
public class ChaosLoggerProperties {

    /**
     * 需要记录日志的前缀
     */
    private String prefix = "/api";

    /**
     * 需要忽略的url
     */
    private String[] ignoreUrls = {};

    /**
     * 是否保存日志到数据库中
     * true 为保存到数据库
     * false 不保存
     */
    private Boolean save = false;

}
