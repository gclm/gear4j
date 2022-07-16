package club.gclmit.gear4j.domain.base;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;

import io.swagger.annotations.ApiModelProperty;

/**
 * Base 实体
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/6/28 16:03
 * @since jdk11
 */
public class BaseDomain implements Serializable {

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

    /**
     * 创建人
     */
    @TableField(value = "create_user", fill = FieldFill.INSERT)
    @ApiModelProperty("创建人")
    private String createUser;

    /**
     * 修改人
     */
    @TableField(value = "update_user", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty("修改人")
    private String updateUser;

    /**
     * 删除
     */
    @TableField(value = "deleted", fill = FieldFill.INSERT)
    @ApiModelProperty("逻辑删除")
    private boolean deleted;

    /**
     * 备注
     */
    @TableField("remark")
    @ApiModelProperty("备注")
    private String remark;

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

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
