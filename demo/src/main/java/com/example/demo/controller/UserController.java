package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/add", produces = {"application/json;charset=UTF-8"})
    public int addUser(User user){
        return userService.addUser(user);
    }
    @GetMapping("/hello")
    public void hello(Model model){
        System.out.println(userService.getUser(1).toString());
        return;
//        return "hello world";
    }
    @GetMapping("/hi")
    public ModelAndView index(Model model){
        model.addAttribute("user",userService.getUser(1));
        return new ModelAndView("index","userModel",model);
    }

//    @ResponseBody
//    @RequestMapping(value = "/all/{pageNum}/{pageSize}", produces = {"application/json;charset=UTF-8"})
//    public Object findAllUser(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize){
//
//        return userService.findAllUser(pageNum,pageSize);
//    }
}
