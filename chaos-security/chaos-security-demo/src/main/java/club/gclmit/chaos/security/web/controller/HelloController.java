package club.gclmit.chaos.security.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * TODO
 * </p>
 *
 * @author: gclm
 * @date: 2019/11/22 15:59
 * @version: V1.0
 * @since 1.8
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "Spring Security hello";
    }
}
