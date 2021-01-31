package club.gclmit.chaos.core.pojo;

import lombok.*;

/**
 * <p>
 * 测试Object
 * </p>
 *
 * @author gclm
 * @date 2020/4/16 6:43 下午
 * @since 1.8
 */
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class User {

    private String id;

    private String name;
}
