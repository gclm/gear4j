package club.gclmit.chaos.web.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import club.gclmit.chaos.core.io.FileUtils;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import javax.servlet.MultipartConfigElement;
import java.util.List;

/**
 * Chaos Web XSS 配置
 *
 * @author gclm
 */
@Slf4j
@Configuration
public class ChaosWebConfig implements WebMvcConfigurer {

	/**
	 * 添加自定义消息解析器
	 *
	 * @author gclm
	 * @param resolvers : 消息解析器list
	 */
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(new QueryHandlerMethodArgumentResolver());
	}

	/**
	 * 文件上传临时路径
	 *
	 * @return MultipartConfigElement
	 */
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		log.info("chaos ---> 配置文件上传的临时路径");
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setLocation(FileUtils.getRootPath());
		return factory.createMultipartConfig();
	}

	/**
	 * 开启 knife4j（Swagger） 映射
	 *
	 * @param registry ResourceHandlerRegistry
	 * @author gclm
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		log.info("chaos ---> 开启 knife4j 映射");
		registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	/**
	 * SpringBoot2.4.0 [allowedOriginPatterns]代替[allowedOrigins]
	 * @author gclm
	 * @param registry CorsRegistry
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		log.info("chaos ---> 开启跨域支持");
		registry.addMapping("/**")
				.allowedOriginPatterns("*")
				.allowedMethods("*")
				.maxAge(3600)
				.allowCredentials(true);
	}

}
