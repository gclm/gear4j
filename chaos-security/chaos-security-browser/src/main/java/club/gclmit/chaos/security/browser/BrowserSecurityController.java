package club.gclmit.chaos.security.browser;


import club.gclmit.chaos.helper.logger.Logger;
import club.gclmit.chaos.helper.logger.LoggerServer;
import club.gclmit.chaos.security.browser.support.SocialUserInfo;
import club.gclmit.chaos.web.response.Result;
import club.gclmit.chaos.security.core.properties.SecurityConstants;
import club.gclmit.chaos.security.core.properties.SecurityProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 *  Security 浏览器端的控制器
 * </p>
 *
 * @author: gclm
 * @date: 2019/12/5 6:03 下午
 * @version: V1.0
 * @since 1.8
 */
@RestController
public class BrowserSecurityController {

    /**
     *  封装了引发跳转请求的工具类，看实现类应该是从 session 中获取的
     */
    private RequestCache requestCache = new HttpSessionRequestCache();

    /**
     *  Spring的 工具类： 封装了所有跳转行为策略类
     */
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    @RequestMapping(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public Result requirAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {

        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if (savedRequest != null) {
            String redirectUrl = savedRequest.getRedirectUrl();
            Logger.info(LoggerServer.SPRING_SECURITY,"引发跳转的请求：[{}]",redirectUrl);
            // 如果是html 请求，则需要跳转到登录页
            if (StringUtils.endsWithIgnoreCase(redirectUrl,".html")) {
                redirectStrategy.sendRedirect(request,response,securityProperties.getBrowser().getLoginPage());
            }
        }
         // 否则都返回需要认证的 json 串
        return Result.fail(HttpStatus.UNAUTHORIZED.value(),"访问的服务需要身份认证，请引导用户到登录页");
    }

    /**
     *  封装 SocialUserInfo 对象
     * @author gclm
     * @param: request
     * @date 2020/1/7 2:17 下午
     * @return: club.gclmit.security.browser.support.SocialUserInfo
     * @throws
     */
    @GetMapping("/social/user")
    public SocialUserInfo getSocialUserInfo(HttpServletRequest request) {
        SocialUserInfo userInfo = new SocialUserInfo();
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
        userInfo.setProviderId(connection.getKey().getProviderId());
        userInfo.setProviderUserId(connection.getKey().getProviderUserId());
        userInfo.setNickname(connection.getDisplayName());
        userInfo.setHeadIng(connection.getImageUrl());
        return userInfo;
    }

}
