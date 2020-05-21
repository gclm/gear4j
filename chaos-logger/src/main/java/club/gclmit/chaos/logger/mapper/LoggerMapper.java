package club.gclmit.chaos.logger.mapper;

import club.gclmit.chaos.logger.pojo.HttpTrace;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Mapper
 * </p>
 *
 * @author: gclm
 * @date: 2020/5/21 6:32 下午
 * @version: V1.0
 * @since 1.8
 */
@Mapper
public interface LoggerMapper extends BaseMapper<HttpTrace> {
}
