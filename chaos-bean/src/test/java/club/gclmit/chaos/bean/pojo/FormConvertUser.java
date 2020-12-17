package club.gclmit.chaos.bean.pojo;

import club.gclmit.chaos.core.util.DateUtils;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

/**
 * 附带类型转换的 用户模型
 *
 * @author gclm
 */
@Data
@Accessors(chain = true)
public class FormConvertUser {

	private Long id;
	private String nickName;
	private Integer age;
	private String phone;
	private String email;
	private String password;
	private Integer gender;
	private String avatar;

	@DateTimeFormat(pattern = DateUtils.PATTERN_DATETIME)
	private LocalDateTime birthday;
}
