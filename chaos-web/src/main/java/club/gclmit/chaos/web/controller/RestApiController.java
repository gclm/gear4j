package club.gclmit.chaos.web.controller;

import club.gclmit.chaos.core.utils.StringUtils;
import club.gclmit.chaos.core.utils.UrlUtils;
import club.gclmit.chaos.web.result.PageResult;
import club.gclmit.chaos.web.result.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  通用 Restful 风格的 CRUD Controller
 * </p>
 *
 * @author gclm
 */
@RestController
public abstract class RestApiController<Service extends IService<T>, T>{

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected Service service;

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

    /**
     * 分页查询所有数据
     *
     * @param queryCondition 分页查询对象
     * @return 所有数据
     */
    @GetMapping
    @ApiOperation(value = "分页查询",httpMethod = "GET")
    public Result list( QueryCondition queryCondition) {
        log.info("分页查询\t:[{}]", StringUtils.toString(queryCondition));
        Page<T> pages = service.page(new Page<>(queryCondition.getPage(), queryCondition.getLimit()));
        return PageResult.ok(pages.getTotal(),pages.getRecords());
    }

    /**
     * 执行查询详情
     *
     * @param id id
     * @return club.gclmit.chaos.response.Result
     * @author gclm
     */
    @ApiOperation(value = "根据id查询数据详情", notes = "根据id查询数据详情")
    @ApiParam(name = "id", required = true, example = "1111")
    @GetMapping("/{id:\\d+}")
    public Result getInfo(@PathVariable String id) {
        Assert.notNull(id, "id不能为空");
        log.info("根据Id:[{}]查询数据详情", id);
        T t = this.service.getById(id);
        return t != null ? Result.ok(t) : Result.fail("执行查询详情操作失败");
    }

    /**
     * 执行更新操作
     *
     * @param t 泛型 T
     * @return club.gclmit.chaos.response.Result
     * @author gclm
     */
    @ApiOperation(value = "更新数据", notes = "更新数据")
    @PutMapping
    public Result update(@Valid @RequestBody T t) {
        Assert.notNull(t, "添加的操作数据为空");
        log.info("更新操作数据:[{}]", StringUtils.toString(t));
        return this.service.updateById(t) ? Result.ok() : Result.fail("执行更新操作失败");
    }

    /**
     * 执行删除操作
     *
     * @param id id
     * @return club.gclmit.chaos.response.Result
     * @author gclm
     */
    @ApiOperation(value = "根据id删除数据", notes = "根据id删除数据")
    @ApiParam(name = "id", required = true, example = "1111")
    @DeleteMapping("/{id:\\d+}")
    public Result delete(@PathVariable String id) {
        Assert.notNull(id, "id不能为空");
        log.info("删除操作数据ID:[{}]", id);
        return this.service.removeById(id) ? Result.ok() : Result.fail("执行删除操作失败");
    }

    /**
     * 批量删除
     *
     * @param ids 采用,拼接的id
     * @return club.gclmit.chaos.web.response.Result
     * @author gclm
     */
    @ApiOperation(value = "批量删除", notes = "批量删除")
    @DeleteMapping("/batch")
    public Result batchDelete(String ids) {
        Assert.notNull(ids, "ids不能为空");
        log.info("批量删除，ids:{}", ids);
        if(UrlUtils.hasUrlEncoded(ids)){
            ids = UrlUtils.decode(ids);
        }
        List<String> idList = Arrays.asList(ids.split(","));
        return this.service.removeByIds(idList) ? Result.ok() : Result.fail("批量删除失败");
    }

}
