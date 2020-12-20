package club.gclmit.chaos.waf.config;

import club.gclmit.chaos.waf.xss.*;
import club.gclmit.chaos.waf.xss.propertis.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.util.List;
import club.gclmit.chaos.core.io.FileUtils;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import javax.servlet.MultipartConfigElement;

/**
 * Chaos Web XSS 配置
 *
 * @author gclm
 */
@Slf4j
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(value = {WafProperties.class})
@ConditionalOnProperty(prefix = "chaos.waf.xss", value = "enabled", havingValue = "true")
public class ChaosXssConfig implements WebMvcConfigurer {

	private final ChaosProperties webProperties;

	//xss 配置

	@Bean
	@ConditionalOnMissingBean
	public XssCleaner xssCleaner() {
		return new DefaultXssCleaner();
	}

	@Bean
	public FormXssClean formXssClean(XssCleaner xssCleaner) {
		return new FormXssClean(xssCleaner);
	}

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer xssJacksonCustomizer(XssCleaner xssCleaner) {
		return builder -> builder.deserializerByType(String.class, new JacksonXssClean(xssCleaner));
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		XssProperties xssProperties = webProperties.getXss();
		List<String> patterns = xssProperties.getPathPatterns();
		if (patterns.isEmpty()) {
			patterns.add("/**");
		}
		XssCleanInterceptor interceptor = new XssCleanInterceptor(xssProperties);
		registry.addInterceptor(interceptor)
			.addPathPatterns(patterns)
			.excludePathPatterns(xssProperties.getExcludePatterns())
			.order(Ordered.LOWEST_PRECEDENCE);
	}

	//基本配置
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
