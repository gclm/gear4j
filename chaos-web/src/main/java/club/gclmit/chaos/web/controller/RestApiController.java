package club.gclmit.chaos.web.controller;

import club.gclmit.chaos.core.util.StringUtils;
import club.gclmit.chaos.web.result.Result;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger log = LoggerFactory.getLogger(this.getClass());

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
        log.info("添加操作数据:[{}]", StringUtils.toString(t));
        return this.service.save(t) ? Result.ok() : Result.fail("执行添加操作失败");
    }

}
