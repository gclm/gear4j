package club.gclmit.gear4j.domain.base;

import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;

/**
 * Base Tree 实体
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/7/17 02:21
 * @since jdk11
 */
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

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public List<T> getChildren() {
        return children;
    }

    public void setChildren(List<T> children) {
        this.children = children;
    }
}
