package club.gclmit.chaos.web.filter.xss;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * jackson xss 处理
 *
 * @author L.cm
 */
@Slf4j
@RequiredArgsConstructor
public class JacksonXssClean extends JsonDeserializer<String> {
	private final XssCleaner xssCleaner;

	@Override
	public String deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
		// XSS filter
		String text = p.getValueAsString();
		if (text == null) {
			return null;
		} else if (XssHolder.isEnabled()) {
			String value = xssCleaner.clean(text);
			log.debug("Json property value:{} cleaned up by mica-xss, current value is:{}.", text, value);
			return value;
		} else {
			return text;
		}
	}

}
