package club.gclmit.chaos.security.core.validate.code;

import org.springframework.security.core.AuthenticationException;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author: gclm
 * @date: 2019/12/12 4:11 下午
 * @version: V1.0
 * @since 1.8
 */
public class ValidateCodeException extends AuthenticationException {

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
