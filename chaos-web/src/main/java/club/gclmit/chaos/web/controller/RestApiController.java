package club.gclmit.chaos.web.controller;


import club.gclmit.chaos.web.response.Result;
import org.springframework.web.bind.annotation.PathVariable;

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

    /**
     *  批量删除
     *
     * @author gclm
     * @param: ids 采用,拼接的id
     * @date 2020/2/22 1:38 下午
     * @return: club.gclmit.chaos.web.response.Result
     * @throws
     */
    public Result batchDelete(String ids);
}
