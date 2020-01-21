package club.gclmit.chaos.security.core.validate.code.sms;

import club.gclmit.chaos.helper.logger.Logger;
import club.gclmit.chaos.helper.logger.LoggerServer;
import club.gclmit.chaos.security.core.properties.SecurityProperties;
import club.gclmit.chaos.security.core.validate.code.ValidateCodeProcessor;
import club.gclmit.chaos.security.core.validate.code.ValidateCode;
import club.gclmit.chaos.security.core.validate.code.ValidateCodeException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author: gclm
 * @date: 2019/12/13 5:07 下午
 * @version: V1.0
 * @since 1.8
 */
public class DefaultSmsCodeSender implements SmsCodeSender {

    @Override
    public void send(String mobile, String code) {
        Logger.info(LoggerServer.SPRING_SECURITY,"手机验证码默认生成器，需要使用者自行实现\n向手机[{}]发送短信验证码：[{}]",mobile,code);
    }

    /**
     * <p>
     *
     * </p>
     *
     * @author: gclm
     * @date: 2019/12/12 4:15 下午
     * @version: V1.0
     * @since 1.8
     */
    public static class SmsCodeFilter extends OncePerRequestFilter implements InitializingBean {

        private AuthenticationFailureHandler authenticationFailureHandler;

        private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

        private Set<String> urls = new HashSet<>();

        private SecurityProperties securityProperties;

        private AntPathMatcher pathMatcher = new AntPathMatcher();

        @Override
        public void afterPropertiesSet() throws ServletException {
            super.afterPropertiesSet();
            String[] configUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(securityProperties.getCode().getSms().getUrl(),",");
            if (ArrayUtils.isNotEmpty(configUrls)) {
                for (String configUrl: configUrls) {
                    urls.add(configUrl);
                }
            }
            urls.add("/authentication/mobile");
        }

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

                /**
                 * 效验请求是否需要拦截，需要拦截则执行验证码效验操作
                 */
                boolean action = false;
                for (String  url: urls) {
                     if (pathMatcher.match(url,request.getRequestURI())) {
                          action = true;
                     }
                }

               if (action) {
                   /**
                    * 图片验证码效验
                    */
                   try {
                       validate(new ServletWebRequest(request));
                   } catch (ValidateCodeException e) {
                       authenticationFailureHandler.onAuthenticationFailure(request,response,e);
                       return;
                   }
               }
               filterChain.doFilter(request,response);
        }

        private void validate(ServletWebRequest request) throws ServletRequestBindingException {
            ValidateCode codeInSession = (ValidateCode) sessionStrategy.getAttribute(request, ValidateCodeProcessor.SESSION_KEY_PREFIX +"SMS");

            String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "smsCode");


            if (StringUtils.isBlank(codeInRequest)) {
                throw new ValidateCodeException("验证码的值不能为空");
            }


            if (codeInSession == null) {
                throw new ValidateCodeException("验证码不存在");
            }

            if (codeInSession.isExpried()) {
                sessionStrategy.removeAttribute(request,ValidateCodeProcessor.SESSION_KEY_PREFIX +"SMS");
                throw new ValidateCodeException("验证码已过期");
            }

            if (!StringUtils.equals(codeInSession.getCode(),codeInRequest)) {
                throw new ValidateCodeException("验证码不匹配");
            }

            sessionStrategy.removeAttribute(request,ValidateCodeProcessor.SESSION_KEY_PREFIX +"SMS");
        }


        public SecurityProperties getSecurityProperties() {
            return securityProperties;
        }

        public void setSecurityProperties(SecurityProperties securityProperties) {
            this.securityProperties = securityProperties;
        }

        public AuthenticationFailureHandler getAuthenticationFailureHandler() {
            return authenticationFailureHandler;
        }

        public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
            this.authenticationFailureHandler = authenticationFailureHandler;
        }

        public SessionStrategy getSessionStrategy() {
            return sessionStrategy;
        }

        public void setSessionStrategy(SessionStrategy sessionStrategy) {
            this.sessionStrategy = sessionStrategy;
        }
    }
}
