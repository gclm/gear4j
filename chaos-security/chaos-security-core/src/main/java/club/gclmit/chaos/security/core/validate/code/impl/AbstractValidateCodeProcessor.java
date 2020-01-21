package club.gclmit.chaos.security.core.validate.code.impl;

import club.gclmit.chaos.security.core.validate.code.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import java.util.Map;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author: gclm
 * @date: 2019/12/13 5:20 下午
 * @version: V1.0
 * @since 1.8
 */
public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {

    /**
     *  操作 session 的工具类
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    @Autowired
    private Map<String, ValidateCodeGenerator> validateCodeGenerates;

    public static final String[] TIPS = {"图片","短信"};

    @Override
    public void create(ServletWebRequest request) throws Exception {
        C validateCode =  generate(request);
        save(request,validateCode);
        send(request,validateCode);
    }

    private C generate(ServletWebRequest request) {
        String type = getValidateCodeType(request).toString().toLowerCase();
        String generatorName = type + ValidateCodeGenerator.class.getSimpleName();
        ValidateCodeGenerator validateCodeGenerator = validateCodeGenerates.get(generatorName);
        if (validateCodeGenerator == null) {
            throw new ValidateCodeException("验证码生成器" + generatorName + "不存在");
        }
        return ( C ) validateCodeGenerator.generate(request);
    }

    private  void save(ServletWebRequest request, C validateCode) {
        sessionStrategy.setAttribute(request,getSessionKey(request),validateCode);
    }

    protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;


    private String getSessionKey(ServletWebRequest request) {
        return  SESSION_KEY_PREFIX + getValidateCodeType(request).toString().toUpperCase();
    }

    /**
     *   效验验证码
     *
     * @author gclm
     * @param: request
     * @date 2019/12/15 5:47 下午
     * @throws
     */
    @Override
    public void validate(ServletWebRequest request) {

        ValidateCodeType processorType = getValidateCodeType(request);
        String sessionKey = getSessionKey(request);

        C codeInSession  = (C) sessionStrategy.getAttribute(request, sessionKey);

        String codeInRequest;

        try {
            codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),processorType.getParamNameOnValidate());
        } catch (ServletRequestBindingException e) {
            throw new ValidateCodeException("获取验证码的值失败");
        }

        String type = tips(processorType);

        if (StringUtils.isBlank(codeInRequest)) {
            throw  new ValidateCodeException(type + "验证码的值不能为空");
        }

        if (codeInSession == null) {
            throw new ValidateCodeException(type + "验证码不存在");
        }

        if (codeInSession.isExpried()) {
            sessionStrategy.removeAttribute(request,sessionKey);
            throw new ValidateCodeException(type + "验证码已过期");
        }

        if (!StringUtils.equals(codeInSession.getCode(),codeInRequest)) {
            throw new ValidateCodeException(type + "验证码不匹配");
        }
        sessionStrategy.removeAttribute(request,sessionKey);
    }


    private String tips(ValidateCodeType processorType) {
        if (ValidateCodeType.IMAGE.equals(processorType)) {
            return  TIPS[0];
        }
        return  TIPS[1];
    }

    /**
     *  根据请求的 url 获取校验码的类型
     *
     * @author gclm
     * @param: request
     * @date 2019/12/15 5:47 下午
     * @return: club.gclmit.security.validate.code.ValidateCodeType
     * @throws
     */
    private ValidateCodeType getValidateCodeType(ServletWebRequest request){

        String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
        return ValidateCodeType.valueOf(type.toUpperCase());
    }

}
