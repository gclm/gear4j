package club.gclmit.chaos.web.controller;

import club.gclmit.chaos.core.lang.LoggerServer;
import club.gclmit.chaos.core.lang.Logger;
import club.gclmit.chaos.core.net.web.UrlUtils;
import club.gclmit.chaos.web.response.Result;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 通用 Restful 风格的 CRUD Controller
 * </p>
 *
 * @author gclm
 */
@RestController
public abstract class ApiController<Service extends IService<T>, T> {

    @Autowired
    protected Service service;

    /**
     * 执行更新操作
     *
     * @author gclm
     * @param t  泛型 T
     * @return club.gclmit.chaos.response.Result
     */
    @ApiOperation(value = "更新数据", notes = "更新数据")
    @PutMapping
    public Result update(@Valid @RequestBody T t) {
        Assert.notNull(t, "添加的操作数据为空");
        Logger.info(LoggerServer.CONTROLLER, "更新操作数据:[{}]", t);
        return this.service.updateById(t) ? Result.ok() : Result.fail("执行更新操作失败");
    }

    /**
     * 执行删除操作
     *
     * @author gclm
     * @param id id
     * @return club.gclmit.chaos.response.Result
     */
    @ApiOperation(value = "根据id删除数据", notes = "根据id删除数据")
    @ApiParam(name = "id", required = true, example = "1111")
    @DeleteMapping("/{id:\\d+}")
    public Result delete(@PathVariable String id) {
        Assert.notNull(id, "id不能为空");
        Logger.info(LoggerServer.CONTROLLER, "删除操作数据ID:[{}]", id);
        return this.service.removeById(id) ? Result.ok() : Result.fail("执行删除操作失败");
    }

    /**
     * 执行查询详情
     *
     * @author gclm
     * @param id id
     * @return club.gclmit.chaos.response.Result
     */
    @ApiOperation(value = "根据id查询数据详情", notes = "根据id查询数据详情")
    @ApiParam(name = "id", required = true, example = "1111")
    @GetMapping("/{id:\\d+}")
    public Result getInfo(@PathVariable String id) {
        Assert.notNull(id, "id不能为空");
        Logger.info(LoggerServer.CONTROLLER, "根据Id:[{}]查询数据详情", id);
        T t = this.service.getById(id);
        return t != null ? Result.ok(t) : Result.fail("执行查询详情操作失败");
    }

    /**
     * 批量删除
     *
     * @author gclm
     * @param ids 采用,拼接的id
     * @return club.gclmit.chaos.web.response.Result
     */
    @ApiOperation(value = "批量删除", notes = "批量删除")
    @DeleteMapping("/batch")
    public Result batchDelete(@RequestBody String ids) {
        Assert.notNull(ids, "ids不能为空");
        Logger.info(LoggerServer.CONTROLLER, "批量删除，ids:{}", ids);
        List<String> idList = Arrays.asList(UrlUtils.decode(ids).split(",")).stream().collect(Collectors.toList());
        return this.service.removeByIds(idList) ? Result.ok() : Result.fail("批量删除失败");
    }
}
