package club.gclmit.chaos.starter.mapper;

import club.gclmit.chaos.storage.properties.FileInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  文件管理
 * </p>
 *
 * @author: gclm
 * @date: 2020/1/15 10:40 上午
 * @version: V1.0
 * @since 1.8
 */
@Mapper
public interface FileMapper extends BaseMapper<FileInfo> {
}
