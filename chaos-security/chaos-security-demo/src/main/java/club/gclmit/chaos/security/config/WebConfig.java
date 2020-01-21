package club.gclmit.chaos.security.config;


import club.gclmit.chaos.helper.logger.Logger;
import club.gclmit.chaos.helper.logger.LoggerServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author: gclm
 * @date: 2019/12/2 23:25
 * @version: V1.0
 * @since 1.8
 */
@Configuration
public class WebConfig implements WebMvcConfigurer{

//    @SuppressWarnings("unused")
//    @Autowired
//    private TimeInterceptor timeInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(timeInterceptor);
//    }
//
//     @Bean
//    public FilterRegistrationBean timeFilter(){
//
//        FilterRegistrationBean filter = new FilterRegistrationBean();
//        TimeFilter timeFilter = new TimeFilter();
//        filter.setFilter(timeFilter);
//        List<String> urls = new ArrayList<>();
//        urls.add("/*");
//        filter.setUrlPatterns(urls);
//        return filter;
//    }

      @Bean
      public ClassLoaderTemplateResolver yourTemplateResolver() {
            Logger.info(LoggerServer.SPRING_BOOT,"加载 template 模板");
            ClassLoaderTemplateResolver configurer = new ClassLoaderTemplateResolver();
            configurer.setPrefix("template/");
            configurer.setSuffix(".html");
            configurer.setTemplateMode(TemplateMode.HTML);
            configurer.setCharacterEncoding("UTF-8");
            /**
             * this is important. This way spring //boot will listen to both places 0 and 1
             */
            configurer.setOrder(0);
            configurer.setCacheable(false);
            configurer.setCheckExistence(true);
            return configurer;
      }

}
