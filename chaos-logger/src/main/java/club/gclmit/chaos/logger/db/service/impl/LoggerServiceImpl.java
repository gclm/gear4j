package club.gclmit.chaos.logger.db.service.impl;

import club.gclmit.chaos.logger.db.mapper.LoggerMapper;
import club.gclmit.chaos.logger.db.pojo.HttpTrace;
import club.gclmit.chaos.logger.db.service.LoggerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  日志服务接口
 * </p>
 *
 * @author 孤城落寞
 * @since 2019-12-17
 */
@Service
public class LoggerServiceImpl extends ServiceImpl<LoggerMapper, HttpTrace> implements LoggerService {


}
