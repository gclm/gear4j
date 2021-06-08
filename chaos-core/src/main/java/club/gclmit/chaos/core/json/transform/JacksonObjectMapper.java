package club.gclmit.chaos.core.json.transform;

import club.gclmit.chaos.core.utils.DateUtils;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Locale;
import java.util.TimeZone;

/**
 * TODO
 *
 * @author gclm
 * @date 2021/6/8 下午4:59
 * @since 1.0
 */
public class JacksonObjectMapper {

    /**
     * 采用单例模式封装 ObjectMapper
     *
     * @return ObjectMapper
     */
    public static ObjectMapper getInstance() {
        return JacksonObjectMapper.JacksonHolder.INSTANCE;
    }

    private static class JacksonHolder {
        private static final ObjectMapper INSTANCE = new JacksonObjectMapper.CustomObjectMapper();
    }

    private static class CustomObjectMapper extends ObjectMapper {

        private static final long serialVersionUID = 1L;

        private static final Locale CHINA = Locale.CHINA;

        CustomObjectMapper() {
            super(jsonFactory());
            super.setLocale(CHINA);
            super.setDateFormat(new SimpleDateFormat(DateUtils.PATTERN_DATETIME, CHINA));
            // 单引号
            super.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
            // 忽略json字符串中不识别的属性
            super.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            // 忽略无法转换的对象
            super.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            super.setTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
            super.findAndRegisterModules();
        }

        private static JsonFactory jsonFactory() {
            return JsonFactory.builder()
                    // 可解析反斜杠引用的所有字符
                    .configure(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true)
                    // 允许JSON字符串包含非引号控制字符（值小于32的ASCII字符，包含制表符和换行符）
                    .configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS, true)
                    .build();
        }
    }
}
