package com.example.demo.controller;

import com.example.demo.model.Authority;
import com.example.demo.model.User;
import com.example.demo.service.AuthorityService;
import com.example.demo.service.UserService;
import com.example.demo.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: demo
 * @Package: com.example.demo.controller
 * @ClassName: ${TYPE_NAME}
 * @Description: java类作用描述
 * @Author: yueChunPeng
 * @CreateDate: 2018-12-22 15:00
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018-12-22 15:00
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
//主页控制器
@Controller
public class MainController {


    private static final Integer ROLE_USER_AUTHORITY_ID = 2;
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String ROLE_USER = "ROLE_USER";

    @Autowired
    private UserService userService;
    @Autowired
    private AuthorityService authorityService;

    @GetMapping("/hi")
    public String test(){
        System.out.println("进入测试函数");
        User user = userService.getUser();
        System.out.println(user.getAuthorityList().toString());

//        System.out.println("controller:____"+user.toString());
        return "index";
    }

    @GetMapping("/")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String boot(){
        return "index";
//        return "redirect:/index";
    }
    @GetMapping("/index")
    public String index(){
        return "index";
    }

    //登录跳转
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    /**
     * 根据用户权限进行分发
     * @param model
     * @return
     */
    @GetMapping("/login-allot")
    public ModelAndView loginAllot(Model model){
        User user = userService.getUser();
        model.addAttribute("user",user);
        String authority = user.getAuthorityList().toString();
        if (authority.contains(ROLE_ADMIN)){
            return new ModelAndView("admin/index","userModel",model);
        }
        else  return new ModelAndView("index","userModel",model);

    }


    /**
     * 跳转至注册页面
     * @return
     */
    @GetMapping("/toRegister")
    public String toRegister(){
        return "register";
    }

    /**
     * 注册用户
     * @param user
     * @return
     */
    @PostMapping("/register")
    public String register(User user){
        System.out.println("进入注册方法");
        System.out.println(user.getUsername()+user.getPassword());
        //创建权限列表
        List<Authority> authorities = new ArrayList<>();
        authorities.add(authorityService.getAuthorityById(ROLE_USER_AUTHORITY_ID));
        //查重避免userId重复
        user.setUserId(UUIDUtil.getUUID());
//        while (userService.isRepeat(user.getUserId())){
//            user.setUserId(UUIDUtil.getUUID());
//        }
        user.setAuthorities(authorities);
        System.out.println(user.toString());

        userService.insertUser(user);
        return "redirect:/login";
    }
}
