package club.gclmit.chaos.web.config;

import club.gclmit.chaos.core.log.Logger;
import club.gclmit.chaos.core.log.LoggerServer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * <p>
 * 配置自定义转换器，解决前端 js long类型数据精度丢失
 * </p>
 *
 * @author gclm
 */
@JsonComponent
public class JsonSerializerManage {

    /**
     * <p>
     *  long变成string
     * </p>
     *
     * @author gclm
     * @param builder  Jackson2ObjectMapperBuilder
     * @return com.fasterxml.jackson.databind.ObjectMapper
     */
    @Bean
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        Logger.info(LoggerServer.CONFIG, "jackson 配置，解决前端 Long 类型精度丢失");
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        //忽略value为null 时 key的输出
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        /**
         * 序列换成json时,将所有的long变成string
         * 因为js中得数字类型不能包含所有的java long值
         */
        SimpleModule module = new SimpleModule();
        module.addSerializer(Long.class, ToStringSerializer.instance);
        module.addSerializer(Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule(module);
        return objectMapper;
    }

}

