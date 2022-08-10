package club.gclmit.gear4j.domain.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * Base 实体
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/6/28 16:03
 * @since jdk11
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class BaseDomain implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	@TableField(value = "create_time", fill = FieldFill.INSERT)
	private Long createTime;

	/**
	 * 修改日期
	 */
	@ApiModelProperty(value = "修改时间")
	@TableField(value = "update_time", fill = FieldFill.UPDATE)
	private Long updateTime;

	/**
	 * 删除
	 */
	@TableField(value = "deleted", fill = FieldFill.INSERT)
	@ApiModelProperty("逻辑删除")
	private Boolean deleted;

    /**
     * 备注
     */
    @TableField("remark")
    @ApiModelProperty("备注")
    private String remark;
}
