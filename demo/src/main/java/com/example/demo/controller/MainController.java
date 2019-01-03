package com.example.demo.controller;

import com.example.demo.model.Authority;
import com.example.demo.model.User;
import com.example.demo.service.AuthorityService;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
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

import java.rmi.MarshalledObject;
import java.util.ArrayList;
import java.util.Date;
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
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;



    @GetMapping("/")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String boot(){
        return "redirect:/product/allProduct";
    }
    @GetMapping("/index")
    public ModelAndView index(Model model){
//        User user = userService.getUser();
//        model.addAttribute("user",user);
        return new ModelAndView("redirect:/","userModel",model);
    }

    //登录跳转
    @GetMapping("/login")
    public String login(){
        return "userLogin";
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
            return new ModelAndView("redirect:/adminIndex");
        }
        else  return new ModelAndView("redirect:/index");

    }
    /*
     * 跳转至管理员首页
     * @param model
     * @return
             */
    @GetMapping("/adminIndex")
    public ModelAndView adminIndex(Model model){
        User user = userService.getUser();
        model.addAttribute("user",user);
        model.addAttribute("userCount",userService.getUserList().size());
        model.addAttribute("categoryCount",categoryService.getCategoryList().size());
        model.addAttribute("productCount",productService.getProductList().size());

        return new ModelAndView("admin/index","userModel",model);

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
        //创建权限列表
        List<Authority> authorities = new ArrayList<>();
        authorities.add(authorityService.getAuthorityById(ROLE_USER_AUTHORITY_ID));
        //查重避免userId重复
        user.setUserId(UUIDUtil.getUUID());
        user.setName("chpyue");
        user.setRegisterTime(new Date());
//        while (userService.isRepeat(user.getUserId())){
//            user.setUserId(UUIDUtil.getUUID());
//        }
        user.setAuthorities(authorities);
        System.out.println(user.toString());

        userService.insertUser(user);
        return "redirect:/login";
    }
}
