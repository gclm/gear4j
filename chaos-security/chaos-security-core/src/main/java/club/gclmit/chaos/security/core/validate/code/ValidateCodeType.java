package club.gclmit.chaos.security.core.validate.code;

import club.gclmit.chaos.security.core.properties.SecurityConstants;

/**
 * <p>
 * 效验方式枚举
 * </p>
 *
 * @author: gclm
 * @date: 2019/12/15 4:37 下午
 * @version: V1.0
 * @since 1.8
 */
public enum  ValidateCodeType {

    /**
     *  短信验证码
     */
     SMS {
        @Override
        public String getParamNameOnValidate() {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_SMS;
        }
    },
    /**
     *  图片验证码
     */
    IMAGE {
        @Override
        public String getParamNameOnValidate() {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_IMAGE;
        }
    };

    /**
     *  效验时从请求中获取参数的名字
     */
    public abstract  String getParamNameOnValidate();
}
