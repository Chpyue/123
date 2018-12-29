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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ProjectName: demo
 * @Package: com.example.demo.controller
 * @ClassName: ${TYPE_NAME}
 * @Description: java类作用描述
 * @Author: yuedashadiao
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

    /**
     * 获取用户列表
     * @param model
     * @return
     */
    @GetMapping("/list")
    public ModelAndView getUserList(Model model){
        List<User> userList = userService.getUserList();
        model.addAttribute("title","用户列表");
        model.addAttribute("userList",userList);

        return new ModelAndView("admin/userList","userModel",model);
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
//        System.out.println(userService.findByUserId(userId).toString());
        return new ModelAndView("admin/profile","userModel",model);
    }

    /**
     * 跳转至用户个人信息修改页面
     * @param userId
     * @param model
     * @return
     */
    @GetMapping("/toUserEdit")
    public ModelAndView toUserEdit(String userId,Model model){
        model.addAttribute("user",userService.findByUserId(userId));
        return new ModelAndView("admin/profileEdit","userModel",model);
    }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    @RequestMapping("/updateUser")
    public String updateUser(User user){
        System.out.println("用户信息="+user.toString());
        userService.updateUser(user);
        if(user.getUsername() == null){
            return "redirect:/user/list";
        }
        return "redirect:/user/"+user.getUserId();
    }


    @PostMapping("/changePassword")
    public String changePassword(User user,HttpServletRequest request){
        String newPassword = request.getParameter("newPassword");
        String reNewPassword = request.getParameter("reNewPassword");
        System.out.println(user.toString());
        System.out.println(newPassword + reNewPassword);
        User oldUser = userService.getUser();
        System.out.println(oldUser.toString());
        if(user.getPassword().equals(oldUser.getPassword())){
            if(newPassword.equals(reNewPassword)){
                user.setPassword(newPassword);
                userService.updateUser(user);
                System.out.println("更新成功");
            }else {
                System.out.println("两次输入的密码不一致");
            }
        }else {
            System.out.println("密码错误");
        }
        return "redirect:/user/toUserEdit?userId="+user.getUserId();
    }

 }
