package club.gclmit.chaos.web.controller;


import club.gclmit.chaos.helper.ObjectHelper;
import club.gclmit.chaos.helper.logger.Logger;
import club.gclmit.chaos.helper.logger.LoggerServer;
import club.gclmit.chaos.web.dto.QueryCondition;
import club.gclmit.chaos.web.swagger.DataType;
import club.gclmit.chaos.web.swagger.ParamType;
import club.gclmit.chaos.web.response.PageResult;
import club.gclmit.chaos.web.response.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
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
public abstract class DefaultRestApiController<Service extends IService<T>, T>  implements RestApiController<T> {

    @Autowired
    protected Service service;

    /**
     * 分页查询
     *
     * @author gclm
     * @param: page     当前页数
     * @param: limit    每页数据大小
     * @date 2019/12/19 4:36 下午
     * @return: club.gclmit.chaos.response.PageResult<T>
     * @throws
     */
    @Override
    @GetMapping
    @ApiOperation(value = "分页查询",notes = "根据查询的page 和 limit进行分页查询",httpMethod = "GET")
    public Result list(QueryCondition queryCondition) {
        Logger.info(LoggerServer.CONTROLLER,"分页查询\tQueryCondition：[{}]", ObjectHelper.toString(queryCondition));
        Page<T> pages = this.service.page(new Page<T>(queryCondition.getPage(),queryCondition.getLimit()));
        return PageResult.ok(pages.getTotal(),pages.getRecords());
    }

    /**
     *  执行添加操作
     * @author gclm
     * @param: t  泛型 T
     * @date 2019/12/17 6:02 下午
     * @return: club.gclmit.chaos.response.Result
     * @throws
     */
    @Override
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

    /**
     *  执行更新操作
     * @author gclm
     * @param: t  泛型 T
     * @date 2019/12/17 6:02 下午
     * @return: club.gclmit.chaos.response.Result
     * @throws
     */
    @Override
    @ApiOperation(value = "更新数据",notes = "更新数据")
    @PutMapping
    public Result update(@Valid @RequestBody T t){
        Assert.notNull(t,"添加的操作数据为空");
        Logger.info(LoggerServer.CONTROLLER, "更新操作数据:[{}]",t);
        if (this.service.updateById(t)) {
            return Result.ok();
        }
        return Result.fail("执行更新操作失败");
    }

    /**
     *  执行删除操作
     * @author gclm
     * @param: id
     * @date 2019/12/17 6:03 下午
     * @return: club.gclmit.chaos.response.Result
     * @throws
     */
    @Override
    @ApiOperation(value = "根据id删除数据" , notes = "根据id删除数据")
    @ApiParam(name = "id",required = true,example ="1111")
    @DeleteMapping("/{id:\\d+}")
    public Result delete(@PathVariable String id) {
        Assert.notNull(id,"id不能为空");
        Logger.info(LoggerServer.CONTROLLER, "删除操作数据ID:[{}]",id);
        if (this.service.removeById(id)) {
            return Result.ok();
        }
        return Result.fail("执行删除操作失败");
    }

    /**
     *  执行查询详情
     * @author gclm
     * @param: id
     * @date 2019/12/17 6:03 下午
     * @return: club.gclmit.chaos.response.Result
     * @throws
     */
    @Override
    @ApiOperation(value = "根据id查询数据详情" , notes = "根据id查询数据详情")
    @ApiParam(name = "id",required = true,example ="1111")
    @GetMapping("/{id:\\d+}")
    public Result getInfo(@PathVariable String id) {
        Assert.notNull(id,"id不能为空");
        Logger.info(LoggerServer.CONTROLLER, "根据Id:[{}]查询数据详情",id);
        T t = this.service.getById(id);
        if (t != null) {
            return Result.ok(t);
        }
        return Result.fail("执行查询详情操作失败");
    }

}
