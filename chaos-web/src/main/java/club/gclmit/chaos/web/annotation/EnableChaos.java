package club.gclmit.chaos.web.annotation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

import java.lang.annotation.*;

/**
 * <p>
 *  Spring Boot启动注解。主要用户自动扫描chaos组件的包
 * </p>
 *
 * @author gclm
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ConditionalOnWebApplication
// 自定义注解配置
@MapperScan(basePackages= {
        "club.gclmit.chaos.logger.mapper",
        "club.gclmit.chaos.*.mapper"
})
@ComponentScan(basePackages={
        "club.gclmit.chaos"
})
public @interface EnableChaos {

}
