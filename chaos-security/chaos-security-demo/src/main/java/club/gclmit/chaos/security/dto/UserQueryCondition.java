package club.gclmit.chaos.security.dto;

import lombok.Data;

/**
 * <p>
 *   查询参数
 * </p>
 *
 * @author: gclm
 * @date: 2019/11/22 17:31
 * @version: V1.0
 * @since 1.8
 */
@Data
public class UserQueryCondition {

    /**
     *  用户名
     */
    private String username;

    /**
     *  年龄
     */
    private Integer age;

    /**
     *  年龄至
     */
    private Integer ageTo;

    /**
     *  默认参数
     */
    private String xxx;

}
