package club.gclmit.chaos.waf.xss;

import club.gclmit.chaos.waf.util.XssHolder;
import club.gclmit.chaos.waf.util.XssUtils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * TODO
 *
 * @author gclm
 */
@Slf4j
public class XssJacksonDeserializer extends JsonDeserializer<String> {

    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        // XSS filter
        String text = jsonParser.getValueAsString();
        if (text == null) {
            return null;
        } else if (XssHolder.isEnabled()) {
            String value = XssUtils.clean(text);
            log.debug("Json property value:{} cleaned up by mica-xss, current value is:{}.", text, value);
            return value;
        } else {
            return text;
        }
    }

}
