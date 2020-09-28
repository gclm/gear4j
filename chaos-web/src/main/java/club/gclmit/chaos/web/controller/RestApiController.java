package club.gclmit.chaos.web.controller;

import club.gclmit.chaos.http.log.LoggerServer;
import club.gclmit.chaos.http.log.Logger;
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
 * @author gclm
 */
@RestController
public abstract class RestApiController<Service extends IService<T>, T>  extends ApiController<Service, T>{

    /**
     *  执行添加操作
     * @author gclm
     * @param t  泛型 T
     * @return club.gclmit.chaos.response.Result
     */
    @ApiOperation(value = "添加数据",notes = "添加数据")
    @PostMapping
    public Result create(@Valid @RequestBody T t) {
        Assert.notNull(t,"添加的操作数据为空");
        Logger.info(LoggerServer.CONTROLLER, "添加操作数据:[{}]",t);
        return this.service.save(t) ? Result.ok() : Result.fail("执行添加操作失败");
    }

}
