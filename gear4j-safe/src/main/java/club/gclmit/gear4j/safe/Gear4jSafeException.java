package club.gclmit.gear4j.safe;

import club.gclmit.gear4j.core.exception.Gear4jException;
import cn.hutool.core.text.CharSequenceUtil;

/**
 * Safe模块Exception
 *
 * @author <a href="https://blog.gclmit.club">gclm</a>
 * @since 2022/7/17 02:38
 * @since jdk11
 */
public class Gear4jSafeException extends Gear4jException {

	private static final long serialVersionUID = 1L;

	public Gear4jSafeException() {
	}

	public Gear4jSafeException(Throwable cause) {
		super(cause);
	}

	public Gear4jSafeException(String message) {
        super(message);
    }

    public Gear4jSafeException(String messageTemplate, Object... params) {
        super(CharSequenceUtil.format(messageTemplate, params));
    }

    public Gear4jSafeException(String message, Throwable cause) {
        super(message, cause);
    }

    public Gear4jSafeException(Throwable cause, String messageTemplate, Object... params) {
        super(CharSequenceUtil.format(messageTemplate, params), cause);
    }
}
