package com.example.demo.controller;

import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.model.ProductView;
import com.example.demo.model.User;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;
import com.example.demo.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;


    /**
     * 商品列表
     * @param model
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView getProductList(Model model){
        List<ProductView> productList=productService.getProductList();
        model.addAttribute("productList",productList);
        model.addAttribute("user",userService.getUser());
        return new ModelAndView("product/Product");
    }

    /**
     * 添加商品
     * @param product
     * @param flag 1.跳转添加页面 2.执行添加操作
     * @param
     * @return
     */
    @RequestMapping("/addproduct")
    public ModelAndView addProduct(MultipartFile file,Product product, String flag, Model model) throws IOException {
        List<Category> categoryList=categoryService.getCategoryList();
        model.addAttribute("user",userService.getUser());
        model.addAttribute("categoryList",categoryList);
        product.setDate(new Date());
        if(flag.equals("1")){
            return new ModelAndView("product/showAddProduct","productModel",model);
        }else {
            product.setImage(FileUtil.saveFile(file,"product"));
            productService.addProduct(product);
            return new ModelAndView("redirect:/product/list");
        }

    }

    /**
     * 修改商品信息
     * @param flag 1.跳转修改页面 2.执行修改操作
     * @param mv
     * @param product 修改的对象
     * @return
     */
    @RequestMapping("/modifiproduct")
    public ModelAndView modifiProduct(String flag, Product product ,Model model){
        List<Category> categoryList=categoryService.getCategoryList();
        model.addAttribute("user",userService.getUser());
        model.addAttribute("categoryList",categoryList);
        if (flag.equals("1")){
            ProductView productView=productService.findByProductId(product.getProductId());
            model.addAttribute("productView",productView);
            return new ModelAndView("product/showModifiProduct","productmodel",model);
        }else {
            System.out.println(product.toString());
            productService.modifiProduct(product);
            return new ModelAndView("redirect:/product/list");
        }
    }

    /**
     * 根据id 删除商品
     * @param productId
     * @param mv
     * @return
     */
    @RequestMapping("/deleteproduct")
    public ModelAndView removeProduct(Integer productId,ModelAndView mv){
        productService.removeProductById(productId);
        mv.setViewName("redirect:/product/list");
        return mv;
    }



}
