package com.example.demo.controller;

import com.example.demo.model.Authority;
import com.example.demo.model.User;
import com.example.demo.service.AuthorityService;
import com.example.demo.service.UserService;
import com.example.demo.utils.Msg;
import com.example.demo.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: demo
 * @Package: com.example.demo.controller
 * @ClassName: ${TYPE_NAME}
 * @Description: java类作用描述
 * @Author: yueChunPeng
 * @CreateDate: 2018-12-19 13:33
 * @UpdateUser: Neil.Zhou
 * @UpdateDate: 2018-12-19 13:33
 * @UpdateRemark: The modified content
 * @Version: 1.0
 */
@Controller
//开启权限认证
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequestMapping(value = "/user")
public class UserController {

    private static final Integer ROLE_USER_AUTHORITY_ID = 2;
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String ROLE_USER = "ROLE_USER";

    @Autowired
    private UserService userService;
    @Autowired
    private AuthorityService authorityService;

    @GetMapping("/list")
    public ModelAndView getUserList(Model model){
        List<User> userList = userService.getUserList();
        model.addAttribute("title","用户列表");
        model.addAttribute("userList",userList);
        return new ModelAndView("user/list","userModel",model);
    }

    /**
     * 通过userId查找对应用户信息
     * @param userId
     * @param model
     * @return
     */
    @GetMapping(value = "/{userId}")
    public ModelAndView getUserInfo(@PathVariable("userId") String userId, Model model){
        model.addAttribute("user",userService.findByUserId(userId));
        model.addAttribute("title","个人信息");
        return new ModelAndView("users/view","userModel",model);
    }
//    @GetMapping("/hello")
////    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public ModelAndView index(Model model){
//        Msg msg = new Msg("测试标题","测试内容","额外信息，只对管理员显示");
//        model.addAttribute("msg",msg);
//        return new ModelAndView("index","userModel",model);
//    }
//    @GetMapping("/hi")
//    public void hi(){
//        User user = new User();
//        user.setUsername("admin");
//        user.setUserId(UUIDUtil.getUUID());
//        user.setPassword("admin");
//        List<Authority> authorities = new ArrayList<>();
//        authorities.add(authorityService.getAuthorityById(1));
//        user.setAuthorities(authorities);
//        userService.insertUser(user);
//    }

//    public UserController(UserService userService) {
//        this.userService = userService;
//    }
//    @GetMapping("/hello")
//    public ModelAndView hello(Model model){
//        model.addAttribute("userList",userService.getUserList());
//        return new ModelAndView("list","userModel",model);
//    }
//    @GetMapping("/add")
//    public void add(Model model){
//        User user = new User();
//        user.setName("chpyue");
//        user.setPassword("123456");
//        user.setUserId(UUIDUtil.getUUID());
//        List<Authority> authorities = new ArrayList<>();
////        Authority authority = new Authority();
//
//        authorities.add(authorityService.getAuthorityById(ROLE_USER_AUTHORITY_ID));
//        user.setAuthorities(authorities);
////        System.out.println("haha");
////        System.out.println(authorities.size());
////        System.out.println(user.toString());
//        userService.saveUser(user);
//        return;
//    }
//    @GetMapping("/user")
//    public void user(Model model){
//        User user=userService.getUser();
//        System.out.println(user.toString());
//    }
//    @GetMapping("/")
//    public Object index(Model model){
//        return "hello world";
//    }
//    @GetMapping("/role")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public String role(){
//        return "you have the role";
//    }




//    @ResponseBody
//    @RequestMapping(value = "/add", produces = {"application/json;charset=UTF-8"})
//    public int addUser(User user){
//        return userService.addUser(user);
//    }
//    @GetMapping("/hello")
//    public void hello(Model model){
//        System.out.println(userService.getUser(1).toString());
//        return;
////        return "hello world";
//    }
//    @GetMapping("/hi")
//    public ModelAndView index(Model model){
//        model.addAttribute("user",userService.getUser(1));
//        return new ModelAndView("index","userModel",model);
//    }

//    @ResponseBody
//    @RequestMapping(value = "/all/{pageNum}/{pageSize}", produces = {"application/json;charset=UTF-8"})
//    public Object findAllUser(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize){
//
//        return userService.findAllUser(pageNum,pageSize);
//    }
 }
