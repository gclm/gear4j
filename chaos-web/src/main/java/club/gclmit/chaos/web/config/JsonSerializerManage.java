package club.gclmit.chaos.web.config;

import club.gclmit.chaos.core.lang.Logger;
import club.gclmit.chaos.core.lang.logger.LoggerServer;
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
 * @author: gclm
 * @date: 2020/1/10 4:09 下午
 * @version: V1.0
 * @since 1.8
 */
@JsonComponent
public class JsonSerializerManage {

    @Bean
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        Logger.debug(LoggerServer.SPRING_BOOT, "jackson 配置，解决前端 Long 类型精度丢失");
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

