package club.gclmit.chaos.swagger;

import club.gclmit.chaos.core.helper.ObjectHelper;
import club.gclmit.chaos.core.helper.logger.Logger;
import club.gclmit.chaos.core.helper.logger.LoggerServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.*;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <p>
 * knife4j 配置文件
 * </p>
 *
 * @author: gclm
 * @date: 2019/12/21 4:31 下午
 * @version: V1.0
 * @since 1.8
 */
@Configuration
@EnableSwagger2
public class Knife4jConfig {

    @Bean
    public Docket createRestApi() {
        ApiInfo apiInfo = apiInfo();
        Logger.info(LoggerServer.SWAGGER,"载入Swagger:{}", ObjectHelper.toString(apiInfo));
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.inskylab.drone.web.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("inskylab无人机载荷云平台")
                .description("inskylab 无人机载荷云平台API")
                .termsOfServiceUrl("http://www.inskylab.com/")
                .contact(new Contact("孤城落寞","https://blog.gclmit.club/","gclm159@gmail.com"))
                .version("1.0")
                .build();
    }
}
