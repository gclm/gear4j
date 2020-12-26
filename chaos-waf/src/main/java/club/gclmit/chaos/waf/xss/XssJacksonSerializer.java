package club.gclmit.chaos.waf.xss;

import club.gclmit.chaos.core.util.StringUtils;
import club.gclmit.chaos.waf.util.XssUtils;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;

/**
 * TODO
 *
 * @author gclm
 */
@Slf4j
public class XssJacksonSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (StringUtils.isNotBlank(value)){
            value = XssUtils.clean(value);
        }
        jsonGenerator.writeString(value);
    }
}
