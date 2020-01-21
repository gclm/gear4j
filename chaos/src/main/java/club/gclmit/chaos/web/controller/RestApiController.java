package club.gclmit.chaos.web.controller;


import club.gclmit.chaos.web.dto.QueryCondition;
import club.gclmit.chaos.web.response.Result;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * Restful 风格的通用 Controller
 * </p>
 *
 * @author: gclm
 * @date: 2020/1/2 12:59 下午
 * @version: V1.0
 * @since 1.8
 */
public interface RestApiController<T> {

    /**
     * 分页查询
     *
     * @summary 分页查询
     * @author gclm
     * @param: queryCondition  查询条件封装
     * @date 2019/12/19 4:36 下午
     * @return: club.gclmit.chaos.response.PageResult<T>
     * @throws
     */
    public Result list(QueryCondition queryCondition);

    /**
     *  执行添加操作
     * @summary 执行添加操作
     * @author gclm
     * @param: t  泛型 T
     * @date 2019/12/17 6:02 下午
     * @return: club.gclmit.chaos.response.Result
     * @throws
     */
    public Result create(T t);

    /**
     *  执行更新操作
     * @summary 执行更新操作
     * @author gclm
     * @param: t  泛型 T
     * @date 2019/12/17 6:02 下午
     * @return: club.gclmit.chaos.response.Result
     * @throws
     */
    public Result update(T t);

    /**
     *  执行删除操作
     * @summary 执行删除操作
     * @author gclm
     * @param: id
     * @date 2019/12/17 6:03 下午
     * @return: club.gclmit.chaos.response.Result
     * @throws
     */
    public Result delete(String id);

    /**
     *  执行查询详情
     * @summary 执行查询详情
     * @author gclm
     * @param: id
     * @date 2019/12/17 6:03 下午
     * @return: club.gclmit.chaos.response.Result
     * @throws
     */
    public Result getInfo(@PathVariable String id);
}
