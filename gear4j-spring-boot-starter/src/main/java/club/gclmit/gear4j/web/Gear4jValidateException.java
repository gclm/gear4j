package club.gclmit.gear4j.web;

import club.gclmit.gear4j.core.exception.Gear4jException;
import cn.hutool.core.text.CharSequenceUtil;

/**
 * 自定义效验异常
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/7/17 02:38
 * @since jdk11
 */
public class Gear4jValidateException extends Gear4jException {

	private static final long serialVersionUID = 1L;

	public Gear4jValidateException() {
	}

	public Gear4jValidateException(String message) {
		super(message);
	}

	public Gear4jValidateException(String messageTemplate, Object... params) {
		super(CharSequenceUtil.format(messageTemplate, params));
	}

	public Gear4jValidateException(String message, Throwable cause) {
		super(message, cause);
	}

	public Gear4jValidateException(Throwable cause, String messageTemplate, Object... params) {
		super(CharSequenceUtil.format(messageTemplate, params), cause);
	}
}
