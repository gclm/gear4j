package club.gclmit.gear4j.web.model.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;

import io.swagger.annotations.ApiModelProperty;

/**
 * 公共字段
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/6/28 16:03
 * @since jdk11
 */
public class PublicField {

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_date", fill = FieldFill.INSERT)
    private Long createDate;

    /**
     * 修改日期
     */
    @ApiModelProperty(value = "修改时间")
    @TableField(value = "update_date", fill = FieldFill.UPDATE)
    private Long updateDate;

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public Long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Long updateDate) {
        this.updateDate = updateDate;
    }
}
