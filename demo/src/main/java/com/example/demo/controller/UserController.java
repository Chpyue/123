package com.example.demo.controller;

import com.example.demo.model.Authority;
import com.example.demo.model.User;
import com.example.demo.service.AuthorityService;
import com.example.demo.service.UserService;
import com.example.demo.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
@RestController
//@RequestMapping(value = "/user")
public class UserController {

    private static final Integer ROLE_USER_AUTHORITY_ID = 1;
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String ROLE_USER = "ROLE_USER";

    @Autowired
    private UserService userService;
    @Autowired
    private AuthorityService authorityService;

//    public UserController(UserService userService) {
//        this.userService = userService;
//    }
    @GetMapping("/hello")
    public ModelAndView hello(Model model){
        model.addAttribute("userList",userService.getUserList());
        return new ModelAndView("list","userModel",model);
    }
    @GetMapping("/add")
    public void add(Model model){
        User user = new User();
        user.setName("chpyue");
        user.setPassword("123456");
        user.setUserId(UUIDUtil.getUUID());
        List<Authority> authorities = new ArrayList<>();
//        Authority authority = new Authority();

        authorities.add(authorityService.getAuthorityById(ROLE_USER_AUTHORITY_ID));
        user.setAuthorities(authorities);
//        System.out.println("haha");
//        System.out.println(authorities.size());
//        System.out.println(user.toString());
        userService.saveUser(user);
        return;
    }



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
