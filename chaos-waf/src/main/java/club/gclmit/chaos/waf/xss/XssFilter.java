package club.gclmit.chaos.waf.xss;

import club.gclmit.chaos.core.utils.UrlUtils;
import club.gclmit.chaos.waf.properties.ChaosWafProperties;
import club.gclmit.chaos.waf.properties.XssProperties;
import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 拦截防止xss注入 通过Jsoup过滤请求参数内的特定字符
 *
 * @author gclm
 */
@Slf4j
@WebFilter(filterName = "xssFilter", urlPatterns = "/*")
public class XssFilter extends OncePerRequestFilter implements Ordered {

    @Autowired
    private ChaosWafProperties wafProperties;

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        XssProperties xss = wafProperties.getXss();
        if (handleUrlRule(request, xss)) {
            filterChain.doFilter(request, response);
            return;
        }

        XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper(request);
        filterChain.doFilter(xssRequest, response);
    }

    /**
     * 处理url规则
     *
     * @param request       HttpServletRequest
     * @param xssProperties : 配置信息
     * @return boolean
     * @author gclm
     */
    private boolean handleUrlRule(HttpServletRequest request, XssProperties xssProperties) {
        String url = request.getServletPath();
        List<String> pathPatterns = xssProperties.getPathPatterns();
        List<String> excludePatterns = xssProperties.getExcludePatterns();

        if (CollUtil.isEmpty(pathPatterns)) {
            return false;
        }

        if (CollUtil.isNotEmpty(excludePatterns) && UrlUtils.isIgnore(excludePatterns, url)) {
            return true;
        }

        return false;
    }
}
