package club.gclmit.chaos.web.controller;

import club.gclmit.chaos.core.logger.LoggerServer;
import club.gclmit.chaos.core.logger.Logger;
import club.gclmit.chaos.web.response.Result;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 *  通用 Restful 风格的 CRUD Controller
 * </p>
 *
 * @author: gclm
 * @date: 2019/12/16 4:37 下午
 * @version: V1.0
 * @since 1.8
 */
@RestController
public abstract class ApiController<Service extends IService<T>, T>  extends RestApiController<Service, T>{

    /**
     *  执行添加操作
     * @author gclm
     * @param: t  泛型 T
     * @date 2019/12/17 6:02 下午
     * @return: club.gclmit.chaos.response.Result
     * @throws
     */
    @ApiOperation(value = "添加数据",notes = "添加数据")
    @PostMapping
    public Result create(@Valid @RequestBody T t) {
        Assert.notNull(t,"添加的操作数据为空");
        Logger.info(LoggerServer.CONTROLLER, "添加操作数据:[{}]",t);
        if (this.service.save(t)) {
            return Result.ok();
        }
        return Result.fail("执行添加操作失败");
    }

}
