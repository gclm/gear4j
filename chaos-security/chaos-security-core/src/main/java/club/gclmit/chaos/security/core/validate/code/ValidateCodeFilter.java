package club.gclmit.chaos.security.core.validate.code;

import club.gclmit.chaos.security.core.properties.SecurityConstants;
import club.gclmit.chaos.security.core.properties.SecurityProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 *  验证码过滤器（图片、短信）
 * </p>
 *
 * @author: gclm
 * @date: 2019/12/12 4:15 下午
 * @version: V1.0
 * @since 1.8
 */
@Component("validateCodeFilter")
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {

    /**
     * 验证码失败处理器
     */
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

    /**
     * 系统配置信息
     */
    @Autowired
    private SecurityProperties securityProperties;

    /**
     *  系统中的校验码处理器
     */
    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;

    /**
     * 存放所有的需要效验验证码的URL
     */
    private Map<String, ValidateCodeType> urlMap = new HashMap<>();

    /**
     *  验证请求url 与 配置的 url 是否匹配的工具类
     */
    private AntPathMatcher pathMatcher = new AntPathMatcher();


    /**
     *  处理华需要蓝的url 配置信息
     */
    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();

        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM , ValidateCodeType.IMAGE);
        addUrlToMap(securityProperties.getCode().getImage().getUrl(), ValidateCodeType.IMAGE);

        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE , ValidateCodeType.SMS);
        addUrlToMap(securityProperties.getCode().getSms().getUrl(), ValidateCodeType.SMS);
    }


    /**
     *  将系统中配置的需要校验码的url的类型存放到 map 中
     * @author gclm
     * @param: urlString
     * @param: type
     * @date 2019/12/15 5:29 下午 void
     * @throws
     */
    protected void addUrlToMap(String urlString, ValidateCodeType type) {
        if (StringUtils.isNotBlank(urlString)) {
            String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString, ",");
            for (String url : urls) {
                urlMap.put(url,type);
            }
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
           ValidateCodeType type = getValidateCodeType(request);

           if (type != null ) {
               logger.info("效验请求（" + request.getRequestURI() + "）中的验证码，验证码类型：" + type);
               try {
                   validateCodeProcessorHolder.findValidateCodeProcessor(type)
                           .validate(new ServletWebRequest(request,response));
                   logger.info("校验码效验通过");
               } catch (ValidateCodeException e) {
                   authenticationFailureHandler.onAuthenticationFailure(request,response,e);
                   return;
               }
           }
           filterChain.doFilter(request,response);
    }


    /**
     *  获取效验码的类型，如果当前请求不需要效验，则返回 null
     * @author gclm
     * @param: request
     * @date 2019/12/15 5:42 下午
     * @return: club.gclmit.security.validate.code.ValidateCodeType
     * @throws
     */
    private ValidateCodeType getValidateCodeType(HttpServletRequest request) {

          ValidateCodeType result = null;
          if (!StringUtils.equalsIgnoreCase(request.getMethod(), "get")) {
              Set<String> urls = urlMap.keySet();
              for (String url: urls) {
                  if (pathMatcher.match(url,request.getRequestURI())) {
                      result = urlMap.get(url);
                  }
              }
          }
          return result;
    }

}
