package club.gclmit.chaos.security.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * <p>
 * 路由控制器
 * </p>
 *
 * @author: gclm
 * @date: 2020/1/7 3:06 下午
 * @version: V1.0
 * @since 1.8
 */
@Controller
@RequestMapping("/")
public class RouteController {

    @GetMapping("/demo-signIn")
    public String signIn(){
        return "demo-signIn";
    }

    @GetMapping("/demo-signUp")
    public String signUp(){
        return "demo-signUp";
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }
}
