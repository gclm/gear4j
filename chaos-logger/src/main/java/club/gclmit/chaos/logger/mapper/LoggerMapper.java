package club.gclmit.chaos.logger.mapper;

import club.gclmit.chaos.logger.model.HttpTrace;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Mapper
 * </p>
 *
 * @author gclm
 */
@Mapper
public interface LoggerMapper extends BaseMapper<HttpTrace> {
}
