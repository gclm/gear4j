package club.gclmit.chaos.json;

import club.gclmit.chaos.core.exception.ChaosException;
import club.gclmit.chaos.core.util.DateUtils;
import club.gclmit.chaos.core.util.StringUtils;
import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

/**
 * <p>
 * 基于jackson 封装
 * </p>
 *
 * @author gclm
 */
@Slf4j
@UtilityClass
public class JsonUtils {

    /**
     * 将对象序列化成json字符串
     *
     * @param object javaBean
     * @return jsonString json字符串
     */
    @Nullable
    public static String toJson(@Nullable Object object) {
        if (object == null) {
            return null;
        }
        try {
            return getInstance().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new ChaosException("jackson 异常",e);
        }
    }

    /**
     * 将对象序列化成 json byte 数组
     *
     * @param object javaBean
     * @return jsonString json字符串
     */
    public static byte[] toJsonAsBytes(@Nullable Object object) {
        if (object == null) {
            return new byte[0];
        }
        try {
            return getInstance().writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            throw new ChaosException("jackson 异常",e);
        }
    }

    /**
     * 将json字符串转成 JsonNode
     *
     * @param jsonString jsonString
     * @return jsonString json字符串
     */
    public static JsonNode readTree(String jsonString) {
        Objects.requireNonNull(jsonString, "jsonString is null");
        try {
            return getInstance().readTree(jsonString);
        } catch (IOException e) {
            throw new ChaosException("jackson 异常",e);
        }
    }

    /**
     * 将json字符串转成 JsonNode
     *
     * @param in InputStream
     * @return jsonString json字符串
     */
    public static JsonNode readTree(InputStream in) {
        Objects.requireNonNull(in, "InputStream in is null");
        try {
            return getInstance().readTree(in);
        } catch (IOException e) {
            throw new ChaosException("jackson 异常",e);
        }
    }

    /**
     * 将json字符串转成 JsonNode
     *
     * @param content content
     * @return jsonString json字符串
     */
    public static JsonNode readTree(byte[] content) {
        Objects.requireNonNull(content, "byte[] content is null");
        try {
            return getInstance().readTree(content);
        } catch (IOException e) {
            throw new ChaosException("jackson 异常",e);
        }
    }

    /**
     * 将json字符串转成 JsonNode
     *
     * @param jsonParser JsonParser
     * @return jsonString json字符串
     */
    public static JsonNode readTree(JsonParser jsonParser) {
        Objects.requireNonNull(jsonParser, "jsonParser is null");
        try {
            return getInstance().readTree(jsonParser);
        } catch (IOException e) {
            throw new ChaosException("jackson 异常",e);
        }
    }

    /**
     * 将json byte 数组反序列化成对象
     *
     * @param content   json bytes
     * @param valueType class
     * @param <T>       T 泛型标记
     * @return Bean
     */
    @Nullable
    public static <T> T readValue(@Nullable byte[] content, Class<T> valueType) {
        if (ObjectUtil.isEmpty(content)) {
            return null;
        }
        try {
            return getInstance().readValue(content, valueType);
        } catch (IOException e) {
            throw new ChaosException("jackson 异常",e);
        }
    }

    /**
     * 将json反序列化成对象
     *
     * @param jsonString jsonString
     * @param valueType  class
     * @param <T>        T 泛型标记
     * @return Bean
     */
    @Nullable
    public static <T> T readValue(@Nullable String jsonString, Class<T> valueType) {
        if (StringUtils.isBlank(jsonString)) {
            return null;
        }
        try {
            return getInstance().readValue(jsonString, valueType);
        } catch (IOException e) {
            throw new ChaosException("jackson 异常",e);
        }
    }

    /**
     * 将json反序列化成对象
     *
     * @param in        InputStream
     * @param valueType class
     * @param <T>       T 泛型标记
     * @return Bean
     */
    @Nullable
    public static <T> T readValue(@Nullable InputStream in, Class<T> valueType) {
        if (in == null) {
            return null;
        }
        try {
            return getInstance().readValue(in, valueType);
        } catch (IOException e) {
            throw new ChaosException("jackson 异常",e);
        }
    }

    /**
     * 将json反序列化成对象
     *
     * @param content       bytes
     * @param typeReference 泛型类型
     * @param <T>           T 泛型标记
     * @return Bean
     */
    @Nullable
    public static <T> T readValue(@Nullable byte[] content, TypeReference<T> typeReference) {
        if (ObjectUtil.isEmpty(content)) {
            return null;
        }
        try {
            return getInstance().readValue(content, typeReference);
        } catch (IOException e) {
            throw new ChaosException("jackson 异常",e);
        }
    }

    /**
     * 将json反序列化成对象
     *
     * @param jsonString    jsonString
     * @param typeReference 泛型类型
     * @param <T>           T 泛型标记
     * @return Bean
     */
    @Nullable
    public static <T> T readValue(@Nullable String jsonString, TypeReference<T> typeReference) {
        if (StringUtils.isBlank(jsonString)) {
            return null;
        }
        try {
            return getInstance().readValue(jsonString, typeReference);
        } catch (IOException e) {
            throw new ChaosException("jackson 异常",e);
        }
    }

    /**
     * 将json反序列化成对象
     *
     * @param in            InputStream
     * @param typeReference 泛型类型
     * @param <T>           T 泛型标记
     * @return Bean
     */
    @Nullable
    public static <T> T readValue(@Nullable InputStream in, TypeReference<T> typeReference) {
        if (in == null) {
            return null;
        }
        try {
            return getInstance().readValue(in, typeReference);
        } catch (IOException e) {
            throw new ChaosException("jackson 异常",e);
        }
    }

    /**
     * 采用单例模式封装 ObjectMapper
     * @return ObjectMapper
     */
    public static ObjectMapper getInstance() {
        return JacksonHolder.INSTANCE;
    }

    private static class JacksonHolder {
        private static final ObjectMapper INSTANCE = new JacksonObjectMapper();
    }

    private static class JacksonObjectMapper extends ObjectMapper {

        private static final long serialVersionUID = 1L;

        private static final Locale CHINA = Locale.CHINA;

        JacksonObjectMapper() {
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