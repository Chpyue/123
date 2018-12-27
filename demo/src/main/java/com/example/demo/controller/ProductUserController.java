package com.example.demo.controller;


import com.example.demo.service.ProductUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@EnableGlobalMethodSecurity(prePostEnabled = true)

@RequestMapping("/product")
public class ProductUserController {

    @Autowired
    private ProductUserService productUserService;

    /**
     * 商品种类表
     * @param model
     * @return
     */
    @RequestMapping("/categoryList")//商品种类列表
    public ModelAndView categoryList(Model model){
        model.addAttribute("categoryList",productUserService.categoryList());
        return new ModelAndView("product/productlist","categoryModel",model);
    }

    /**
     * 全部商品按钮
     * @param model
     * @param orderbyname
     * @return
     */
    @RequestMapping("/allProduct")//所有商品页面
    public ModelAndView toList(Model model,String orderbyname) {
        model.addAttribute("allProductList",productUserService.findAll(orderbyname));
        return new ModelAndView("product/productlist","productModel",model);
    }

    /**
     * 搜索框
     * @param model
     * @param name
     * @param orderbyname
     * @return
     */
    @RequestMapping("/findProduct")//查找商品
    public ModelAndView findProduct(Model model,String name, String orderbyname){
        model.addAttribute("findProductList",productUserService.findByName(name, orderbyname));
        return new ModelAndView("product/productlist","productModel",model);
    }

    /**
     * 种类按钮
     * @param model
     * @param kind
     * @param orderbyname
     * @return
     */
    @RequestMapping("findKindProduct")//按种类显示商品
    public ModelAndView findKindProduct(Model model,int kind, String orderbyname){
        model.addAttribute("findKindProductList",productUserService.findByKind(kind, orderbyname));
        return new ModelAndView("product/productlist","productModel",model);
    }

    /**
     * 商品详情页面
     * @param model
     * @param productId
     * @return
     */
    @RequestMapping("productInfo")//单个商品
    public ModelAndView productInfo(Model model,int productId){
        model.addAttribute("findKindProductList",productUserService.productInfo(productId));
        return new ModelAndView("product/productinfo","productModel",model);
    }

}
