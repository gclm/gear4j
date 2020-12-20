package club.gclmit.chaos.web.filter.xss;

import club.gclmit.chaos.core.lang.StringPool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import java.beans.PropertyEditorSupport;

/**
 * 表单 xss 处理
 *
 * @author L.cm
 */
@ControllerAdvice
@RequiredArgsConstructor
public class FormXssClean {

	private final XssCleaner xssCleaner;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// 处理前端传来的表单字符串
		binder.registerCustomEditor(String.class, new StringPropertiesEditor(xssCleaner));
	}

	@Slf4j
	@RequiredArgsConstructor
	public static class StringPropertiesEditor extends PropertyEditorSupport {
		private final XssCleaner xssCleaner;

		@Override
		public String getAsText() {
			Object value = getValue();
			return value != null ? value.toString() : StringPool.EMPTY;
		}

		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			if (text == null) {
				setValue(null);
			} else if (XssHolder.isEnabled()) {
				String value = xssCleaner.clean(text);
				setValue(value);
				log.debug("Request parameter value:{} cleaned up by mica-xss, current value is:{}.", text, value);
			} else {
				setValue(text);
			}
		}
	}

}
