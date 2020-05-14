package club.gclmit.chaos.web.xss;

import club.gclmit.chaos.core.net.html.EscapeUtils;
import club.gclmit.chaos.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * <p>
 * Xss 过滤实现
 * </p>
 *
 * @author: gclm
 * @version: V1.0
 * @since 1.8
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    public XssHttpServletRequestWrapper(HttpServletRequest servletRequest) {
        super(servletRequest);
    }

    @Override
    public String[] getParameterValues(String parameter) {
        String[] values = super.getParameterValues(parameter);
        if (values == null) {
            return null;
        }
        int count = values.length;
        String[] encodedValues = new String[count];
        for (int i = 0; i < count; i++) {
            //数字类型一律不进行过滤--数字过滤个鸡儿
            if (StringUtils.isNumeric(values[i])) {
                encodedValues[i] = (values[i]);
            } else {

                log.debug("转义之前:{}", values[i]);
                encodedValues[i] = EscapeUtils.escape(values[i]).trim();
                log.debug("转义之后:{}", encodedValues[i]);
            }
        }
        return encodedValues;
    }

    @Override
    public String getParameter(String parameter) {
        String value = super.getParameter(parameter);
        if (value == null) {
            return null;
        }
        if (StringUtils.isNumeric(value)) {
            return value;
        }
        return EscapeUtils.escape(value).trim();

    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);
        if (value == null) {
            return null;
        }
        if (StringUtils.isNumeric(value)) {
            return value;
        }
        return EscapeUtils.escape(value).trim();
    }

}
