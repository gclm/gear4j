package club.gclmit.chaos.bean.pojo;

import lombok.Data;

/**
 * 来源用户
 *
 * @author gclm
 */
@Data
public class FormUser {

	private Long id;
	private String nickName;
	private Integer age;
	private String phone;
	private String email;
	private String password;
	private Integer gender;
	private String avatar;


}
