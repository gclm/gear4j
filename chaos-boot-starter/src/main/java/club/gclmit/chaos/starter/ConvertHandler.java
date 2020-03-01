package club.gclmit.chaos.starter;

import club.gclmit.chaos.storage.properties.Storage;
import org.mapstruct.Mapper;

/**
 * <p>
 * Dto 转换 Mapper 工具类
 * </p>
 *
 * @author: gclm
 * @date: 2020/2/19 3:20 下午
 * @version: V1.0
 * @since 1.8
 */
@Mapper(componentModel = "spring")
public interface ConvertHandler {

    /**
     *  StorageProperties --> Storage
     */
    Storage from(StorageProperties storageProperties);
}
