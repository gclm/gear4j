package club.gclmit.chaos.security.web.controller;

import club.gclmit.chaos.helper.id.IDHelper;
import club.gclmit.chaos.security.dto.User;
import club.gclmit.chaos.security.dto.UserQueryCondition;
import club.gclmit.chaos.web.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * User Controller
 * </p>
 *
 * @author: gclm
 * @date: 2019/11/22 15:05
 * @version: V1.0
 * @since 1.8
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    @PostMapping("/regist")
    public void regist(User user, HttpServletRequest request) {
        String username = IDHelper.getStringId();
        providerSignInUtils.doPostSignUp(username,new ServletWebRequest(request));
    }

    /**
     *  spring 自动注入
     */
    @GetMapping("/me")
    public Result me(@AuthenticationPrincipal UserDetails userDetails){
        return Result.ok(userDetails);
    }


    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.info("创建用户:{}",user);
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user){
        log.info("更新用户:{}",user);
        user.setId("1");
        return user;
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(@PathVariable String id) {
        log.info("删除用户ID：{}",id);
    }

   @GetMapping
   public List<User> query(UserQueryCondition condition, Pageable pageable) {

      log.info("查询参数:{}",ReflectionToStringBuilder.toString(condition, ToStringStyle.SHORT_PREFIX_STYLE));

      log.info("每页数量：{}",pageable.getPageSize());
      log.info("当前页数：{}",pageable.getPageNumber());
      log.info("排序：{}",pageable.getSort());

      List<User> users = new ArrayList<> ();
      users.add(new User());
      users.add(new User());
      users.add(new User());
      users.add(new User());

      return  users;
   }


   @GetMapping("/{id:\\d+}")
   public User getInfo(@PathVariable String id) {
       log.info("进入 getInfo 服务 ---> Id:{}",id);

       User user = new User();
       user.setId(id);
       user.setUsername("tom");
       user.setBirthday(new Date());
       return user;
   }
}
