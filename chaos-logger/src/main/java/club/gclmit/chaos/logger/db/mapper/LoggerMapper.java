package club.gclmit.chaos.logger.db.mapper;

import club.gclmit.chaos.logger.db.pojo.HttpTrace;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  日志插入数据库
 * </p>
 *
 * @author: gclm
 * @date: 2020/1/15 10:40 上午
 * @version: V1.0
 * @since 1.8
 */
@Mapper
public interface LoggerMapper extends BaseMapper<HttpTrace> {
}
