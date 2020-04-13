package club.gclmit.chaos.web.controller;

import club.gclmit.chaos.core.logger.LoggerServer;
import club.gclmit.chaos.core.logger.Logger;
import club.gclmit.chaos.core.helper.ObjectHelper;
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
 * @author: gclm
 * @date: 2019/12/16 4:37 下午
 * @version: V1.0
 * @since 1.8
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
        Logger.info(LoggerServer.CONTROLLER,"分页查询\t:[{}]", ObjectHelper.toString(query));
        Page<T> pages = service.page(new Page<>(query.getPage(),query.getLimit()));
        return PageResult.ok(pages.getTotal(),pages.getRecords());
    }
}
