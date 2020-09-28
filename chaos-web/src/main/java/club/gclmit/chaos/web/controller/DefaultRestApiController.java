package club.gclmit.chaos.web.controller;

import club.gclmit.chaos.core.log.LoggerServer;
import club.gclmit.chaos.core.log.Logger;
import club.gclmit.chaos.core.util.ObjectUtils;
import club.gclmit.chaos.web.response.PageResult;
import club.gclmit.chaos.web.response.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  通用 Restful 风格的 CRUD Controller
 * </p>
 *
 * @author gclm
 */
@RestController
public abstract class DefaultRestApiController<Service extends IService<T>, T>  extends RestApiController<Service, T> {

    /**
     * 分页查询所有数据
     *
     * @param query 分页查询对象
     * @return 所有数据
     */
    @GetMapping
    @ApiOperation(value = "分页查询",httpMethod = "GET")
    public Result list(QueryCondition query) {
        Logger.info(LoggerServer.CONTROLLER,"分页查询\t:[{}]", ObjectUtils.toString(query));
        Page<T> pages = service.page(new Page<>(query.getPage(),query.getLimit()));
        return PageResult.ok(pages.getTotal(),pages.getRecords());
    }
}
