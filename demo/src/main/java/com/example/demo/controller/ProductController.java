package com.example.demo.controller;

import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    /**
     * 商品列表
     * @param model
     * @return
     */
    @RequestMapping("/list")
    public ModelAndView getProductList(Model model){
        List<Product> productList=productService.getProductList();
//        System.out.println("这个商品的种类id是");
        model.addAttribute("productList",productList);
//        for (int i=0;i<productList.size();i++){
//            Category category=categoryService.findByCategoryId(productList.get(i).getCategoryId());
//
//            model.addAttribute("category",category);
//        }

        return new ModelAndView("product/Product");
    }

    /**
     * 添加商品
     * @param product
     * @param flag 1.跳转添加页面 2.执行添加操作
     * @param mv
     * @return
     */
    @RequestMapping("/addproduct")
    public ModelAndView addProduct(@ModelAttribute Product product,String flag,ModelAndView mv){
        if(flag.equals("1")){
            mv.setViewName("product/showAddProduct");
        }else {
            System.out.println("---------------");
            productService.addProduct(product);

            mv.setViewName("redirect:product/list");
        }
        return mv;
    }

    /**
     * 修改商品信息
     * @param flag 1.跳转修改页面 2.执行修改操作
     * @param mv
     * @param product 修改的对象
     * @return
     */
    @RequestMapping("/modifiproduct")
    public ModelAndView modifiProduct(String flag,ModelAndView mv,@ModelAttribute Product product){
        if (flag.equals("1")){
            Product product1=productService.findByProductId(product.getProductId());
            mv.addObject("product",product1);
            mv.setViewName("product/showModifiProduct");
        }else {
            productService.modifiProduct(product);
            mv.setViewName("redirect:product/list");
        }
        return mv;
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
        mv.setViewName("redirect:product/list");
        return mv;
    }



}
