package club.gclmit.chaos.security.core.validate.code;

import java.time.LocalDateTime;

/**
 * <p>
 *  图片验证码
 * </p>
 *
 * @author: gclm
 * @date: 2019/12/12 3:47 下午
 * @version: V1.0
 * @since 1.8
 */
public class ValidateCode  {

    /**
     *  验证码
     */
    private String code;

    /**
     *  过期时间
     */
    private LocalDateTime expireTime;

    public ValidateCode() {
    }

    /**
     * @param: imageCode
     * @param: code
     * @param: expireIn  过期时间，单位秒
     * @date 2019/12/12 3:53 下午
     * @throws
     */
    public ValidateCode(String code, int expireIn) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    public ValidateCode(String code, LocalDateTime expireTime) {
        this.code = code;
        this.expireTime = expireTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public  boolean isExpried() {
        return this.expireTime.isBefore(LocalDateTime.now());
   }
}
