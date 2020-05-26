package club.gclmit.chaos.web.xss;

import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.function.Supplier;
import java.util.regex.Pattern;


/**
 * <p>
 * Xss 过滤器
 * </p>
 *
 * @author gclm
 */
public class XssFilter implements Filter {

    public XssFilter(Supplier<Boolean> supplier) {
        this.supplier = supplier;
    }

    FilterConfig filterConfig = null;

    /**
     * 需要放行的url
     */
    private String[] releaseUrl = new String[]{"/open/list", "/login/**"};

    /**
     * 正则表达式过滤规则
     * .js  .css  .woff    .gi .json    .png    .jpg    .jpeg .ico  这些静态资源
     * 比如   public static Pattern p = Pattern.compile("^.*\\.((?!html).)*$");
     */
    private Pattern pattern = Pattern.compile("^.*\\.((?!html).)*$");

    /**
     * 定义的一个开关,用来动态开启xss,如果生产项目,参考这个搬去用就行拉,  releaseUrl 也可以参考,这样级就可以动态处理了
     */
    private Supplier<Boolean> supplier;

    /**
     * @return
     */
    private Boolean getEnabled() {
        return supplier.get() == null ? false : supplier.get();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String servletPath = request.getServletPath();

        //正则校验一次  专门对于那些  .js  .css  .woff    .gi .json    .png    .jpg    .jpeg .ico  这些静态资源
        if (pattern != null && pattern.matcher(servletPath).find()) {
            chain.doFilter(request, response);
            return;
        }

        //判断已经开启过滤,然后确定是否是排除的接口
        if (getEnabled() && !PatternMatchUtils.simpleMatch(releaseUrl, servletPath)) {
            chain.doFilter(new XssHttpServletRequestWrapper(request), response);
        } else {
            chain.doFilter(request, response);
        }
    }

}
