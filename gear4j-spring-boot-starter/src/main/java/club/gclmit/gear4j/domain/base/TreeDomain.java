package club.gclmit.gear4j.domain.base;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

/**
 * Base Tree 实体
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/7/17 02:21
 * @since jdk11
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TreeDomain<T> extends BaseDomain {

    /**
     * 父级编号
     */
    @TableField("parent")
    private String parent;

    /**
     * 子级集合
     */
    @TableField(exist = false)
    private List<T> children = new ArrayList<>();
}
