package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.AuthorityService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
        model.addAttribute("title","啊哈哈");
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
        System.out.println(userId);
        model.addAttribute("user",userService.findByUserId(userId));
        model.addAttribute("title","个人信息");
        System.out.println(userService.findByUserId(userId).toString());
        return new ModelAndView("admin/profile","userModel",model);
    }

 }
