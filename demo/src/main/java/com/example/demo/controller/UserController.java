package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.AuthorityService;
import com.example.demo.service.UserService;
import com.example.demo.utils.DateToString;
import com.example.demo.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView getUserList(Model model){
        List<User> userList = userService.getUserList();
        model.addAttribute("title","用户列表");
        model.addAttribute("userList",userList);
        //获取当前用户信息
        model.addAttribute("user",userService.getUser());

        return new ModelAndView("admin/userList","userModel",model);
    }

    /**
     * 获取管理员列表
     * @param model
     * @return
     */
    @GetMapping("/adminlist")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView getAdminList(Model model){
        List<User> adminList = userService.getAdminList();
        model.addAttribute("title","用户列表");
        model.addAttribute("adminList",adminList);
        //获取当前用户信息
        model.addAttribute("user",userService.getUser());

        return new ModelAndView("admin/adminList","userModel",model);
    }


    /**
     * 通过userId查找对应用户信息（管理员）
     * @param userId
     * @param model
     * @return
     */
    @GetMapping(value = "/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView getUserInfo(@PathVariable("userId") String userId, Model model){

        //所查询用户信息
        model.addAttribute("users",userService.findByUserId(userId));
        //获取当前用户信息
        model.addAttribute("user",userService.getUser());
        //日期格式化对象
        model.addAttribute("ds", new DateToString());
        return new ModelAndView("admin/profile","userModel",model);
    }

    /**
     * 跳转至用户个人信息修改页面（管理员）
     * @param userId
     * @param model
     * @return
     */
    @GetMapping("/toUserEdit")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView toUserEdit(String userId,Model model){
        //所查询用户信息
        model.addAttribute("users",userService.findByUserId(userId));
        //获取当前用户信息
        model.addAttribute("user",userService.getUser());
        //日期格式化对象
        model.addAttribute("ds", new DateToString());
        return new ModelAndView("admin/profileEdit","userModel",model);
    }

    /**
     * 修改个人头像
     * （仅允许修改本人）
     * @param file
     * @return
     */
    @PostMapping("/updateImage")
    @PreAuthorize("isAuthenticated()")
    public String updateImage(MultipartFile file) throws IOException {
        User user = userService.getUser();
        user.setPortraitUrl(FileUtil.saveFile(file,"portrait"));
        userService.updateUser(user);
        return "redirect:/user/"+user.getUserId();
    }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    @RequestMapping("/updateUser")
    @PreAuthorize("isAuthenticated()")
    public String updateUser(User user){
        System.out.println("用户信息="+user.toString());
        userService.updateUser(user);
        if(user.getUsername() == null){
            return "redirect:/user/list";
        }
        return "redirect:/user/"+user.getUserId();
    }


    @PostMapping("/changePassword")
    @PreAuthorize("isAuthenticated()")
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
