package club.gclmit.chaos.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;


/**
 * <p>
 *
 * </p>
 *
 * @author: gclm
 * @date: 2019/11/22 15:07
 * @version: V1.0
 * @since 1.8
 */
@Getter
@Setter
@ToString
public class User {

    /**
     *  id
     */
    private String id;

    /**
     *  账号
     */
    private String username;

    /**
     *  密码
     */
    private String password;

    /**
     *  生日
     */
    private Date birthday;
}
