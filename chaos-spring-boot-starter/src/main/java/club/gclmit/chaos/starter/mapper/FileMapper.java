package club.gclmit.chaos.starter.mapper;

import club.gclmit.chaos.storage.model.FileInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  文件管理
 * </p>
 *
 * @author gclm
 */
@Mapper
public interface FileMapper extends BaseMapper<FileInfo> {
}
